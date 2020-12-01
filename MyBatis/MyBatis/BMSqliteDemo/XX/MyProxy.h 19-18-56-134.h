//
//  MyProxy.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/2.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "InvocationHandler.h"

NS_ASSUME_NONNULL_BEGIN

#define CreateProxyInstanceFor(C) (id<C>)[MyProxy createProxyInstance:C.class]

@interface MyProxy : NSProxy

+ (id)createProxyInstance:(id<InvocationHandler>)handler;

@end

NS_ASSUME_NONNULL_END
