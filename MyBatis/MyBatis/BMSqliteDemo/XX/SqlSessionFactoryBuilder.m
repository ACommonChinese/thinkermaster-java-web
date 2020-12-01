//
//  SqlSessionFactoryBuilder.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "SqlSessionFactoryBuilder.h"
#import "Configuration.h"
#import "XMLConfigBuilder.h"
#import "DefaultSqlSessionFactory.h"

@implementation SqlSessionFactoryBuilder

- (id<SqlSessionFactory>)build:(NSString *)configPath {
    Configuration *config = [XMLConfigBuilder loadConfiguration:configPath];
    return [[DefaultSqlSessionFactory alloc] initWithConfig:config];
}

@end
