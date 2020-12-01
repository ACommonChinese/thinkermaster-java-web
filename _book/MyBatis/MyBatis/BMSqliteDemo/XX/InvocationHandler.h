//
//  InvocationHandler.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/2.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Field.h"

NS_ASSUME_NONNULL_BEGIN

@protocol InvocationHandler <NSObject>

// InvocationHandler.java
// public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
- (void)invoke:(SEL)method args:(NSArray<Field *> *)args;

@end

NS_ASSUME_NONNULL_END
