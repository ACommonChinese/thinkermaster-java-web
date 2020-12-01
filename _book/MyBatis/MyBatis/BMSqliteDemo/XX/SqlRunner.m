//
//  SqlRunner.m
//  ProxyDemo
//
//  Created by liuxing8807@126.com on 2019/11/4.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "SqlRunner.h"

@interface SqlRunner ()

{
    sqlite3 *_db;
}

@end

// org.apache.ibatis.jdbc.SqlRunner
@implementation SqlRunner

// - (void)selectOne:(NSString *)sql

// SQL + 参数
- (void)selectAll:(NSString *)sql, ... {
    sqlite3_stmt *stmt = [self getPrepareStatement:sql];
    va_list args;
    va_start(args, sql);
    // sqlite3_bind_int(<#sqlite3_stmt *#>, <#int#>, <#int#>)
}

- (void)setParameters:(sqlite3_stmt *)stmt _args:(va_list)pArgs {
//    va_list args;
//    va_start(args, @"");
    
}

// 方法名，参数个数，参数列表，返回值类型
- (void)runSelectSql:(NSString *)sql, ... {
    sqlite3_stmt *stmt = [self getPrepareStatement:sql];
    // sqlite3_bind_text(<#sqlite3_stmt *#>, <#int#>, <#const char *#>, <#int#>, <#void (*)(void *)#>)
    
}

- (void)runUpdateSql:(NSString *)sql {}
- (void)runInsertSql:(NSString *)sql {}
- (void)runModifySql:(NSString *)sql {}

- (sqlite3_stmt *)getPrepareStatement:(NSString *)sql {
    sqlite3_stmt *stmt;
    int result = sqlite3_prepare_v2(_db, sql.UTF8String, -1, &stmt, NULL);
    if (result == SQLITE_OK) {
        return stmt;
    }
    return NULL;
}

@end
