//
//  SqlSessionFactoryBuilder.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SqlSessionFactory.h"

NS_ASSUME_NONNULL_BEGIN

@interface SqlSessionFactoryBuilder : NSObject

- (id<SqlSessionFactory>)build:(NSString *)configPath;

@end

NS_ASSUME_NONNULL_END
