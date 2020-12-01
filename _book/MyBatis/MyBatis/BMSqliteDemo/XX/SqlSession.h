//
//  SqlSession.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol SqlSession <NSObject>

- (id)getMapper:(Protocol *)protocol;

/**
 关闭数据库，释放资源
 */
- (BOOL)close;

@end

NS_ASSUME_NONNULL_END
