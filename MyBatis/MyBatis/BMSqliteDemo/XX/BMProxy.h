//
//  BMProxy.h
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>

#define BMProxyFor(P) (id<P>)[[BMProxy alloc] initWithProtocol:@protocol(P)]

@interface BMProxy : NSProxy

- (id)initWithProtocol:(Protocol *)object;
+ (id)proxyWithObject:(Protocol *)object;

@end

