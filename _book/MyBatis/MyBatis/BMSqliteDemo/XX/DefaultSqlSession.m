//
//  DefaultSqlSession.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "DefaultSqlSession.h"
#import "SqlSession.h"
#import <sqlite3.h>
#import "MyProxy.h"
#import "MapperProxy.h"

@interface DefaultSqlSession ()
{
    sqlite3 *_db;
}

@property (nonatomic, strong) Configuration *config;
@property (nonatomic, assign) NSInteger errorCode;
// @property (nonatomic, strong) SqlError *error;

@end

@implementation DefaultSqlSession

- (instancetype)initWithConfig:(Configuration *)config {
    if (self = [super init]) {
        self.config = config;
        int result = sqlite3_open(config.dbPath.UTF8String, &_db);
        if (result == SQLITE_OK) {
            NSLog(@"数据库打开成功");
        } else {
            NSLog(@"数据库打开失败");
            // self.error = SqlError.openError;
            int result = sqlite3_close(_db);
            if (result == SQLITE_OK) {
                _db = nil;
            } else {
                NSLog(@"关闭数据库失败: %d", result);
            }
        }
    }
    return self;
}

- (id)getMapper:(Protocol *)protocol {
    MapperProxy *proxy = [[MapperProxy alloc] init];
    proxy.protocol = protocol;
    proxy.mappers = self.config.mappers;
    return [MyProxy createProxyInstance:proxy];
}

// 参考 YYKVStorage.m: _dbClose
- (BOOL)close {
    if (!_db) return YES;
    
    int  result = 0;
    BOOL retry = NO;
    BOOL stmtFinalized = NO;
    
    do {
        retry = NO;
        result = sqlite3_close(_db);
        if (result == SQLITE_BUSY || result == SQLITE_LOCKED) {
            if (!stmtFinalized) {
                stmtFinalized = YES;
                sqlite3_stmt *stmt;
                while ((stmt = sqlite3_next_stmt(_db, nil)) != 0) {
                    sqlite3_finalize(stmt);
                    retry = YES;
                }
            }
        } else if (result != SQLITE_OK) {
            if (_errorLogsEnabled) {
                NSLog(@"%s line:%d sqlite close failed (%d).", __FUNCTION__, __LINE__, result);
            }
        }
    } while (retry);
    _db = NULL;
    return YES;
}

@end
