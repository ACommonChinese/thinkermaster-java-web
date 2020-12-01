//
//  DefaultSqlSessionFactory.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "DefaultSqlSessionFactory.h"
#import "DefaultSqlSession.h"

@interface DefaultSqlSessionFactory ()

@property (nonatomic, strong) Configuration *config;

@end

@implementation DefaultSqlSessionFactory

- (instancetype)initWithConfig:(Configuration *)config {
    if (self = [super init]) {
        self.config = config;
    }
    return self;
}

- (id<SqlSession>)openSession {
    return [[DefaultSqlSession alloc] initWithConfig:self.config];
}

@end
