//
//  DefaultSqlSession.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SqlSession.h"
#import "Configuration.h"

NS_ASSUME_NONNULL_BEGIN

@interface DefaultSqlSession : NSObject <SqlSession>

- (instancetype)initWithConfig:(Configuration *)config;

@end

NS_ASSUME_NONNULL_END
