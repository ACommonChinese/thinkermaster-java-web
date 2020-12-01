//
//  BMSqlSessionFactoryBuilder.h
//  BMSqlite
//
//  Created by banma-1118 on 2019/11/15.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BMSqlSessionFactory.h"

NS_ASSUME_NONNULL_BEGIN

@interface BMSqlSessionFactoryBuilder : NSObject

- (id<SqlSessionFactory>)build:(NSString *)configPath;
- (id<SqlSessionFactory>)build:(NSString *)configPath dbPath:(NSString *)dbPath;

@end

NS_ASSUME_NONNULL_END
