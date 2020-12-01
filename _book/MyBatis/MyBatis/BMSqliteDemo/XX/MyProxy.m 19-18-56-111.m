//
//  MyProxy.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/2.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

//public <T> T getMapper(Class<T> daoInterfaceClass) {
//       return (T) Proxy.newProxyInstance(daoInterfaceClass.getClassLoader(),
//               new Class[]{daoInterfaceClass},new MapperProxy(cfg.getMappers(),connection));
//   }

#import "MyProxy.h"
#import <objc/runtime.h>

@interface MyProxy ()

@property (nonatomic, strong) id<InvocationHandler> invocationHandler;

@end

@implementation MyProxy

// 1.查询该方法的方法签名
- (NSMethodSignature *)methodSignatureForSelector:(SEL)sel {
    NSLog(@"%@", NSStringFromSelector(sel)); // getUser:
    return [(NSObject *)self.invocationHandler methodSignatureForSelector:@selector(invoke:)];
}

// 2.有了方法签名之后就会调用方法实现
// 转发到InvocationHandler的invoke方法上
- (void)forwardInvocation:(NSInvocation *)invocation {
    // https://www.jianshu.com/p/20c441f19126
    // https://mazyod.com/blog/2014/03/10/nsproxy-with-uikit/
    NSLog(@"selector: %@", NSStringFromSelector(invocation.selector)); // getUser:
    NSLog(@"target: %@", invocation.target); // MyProxy
    NSMethodSignature *signature = [invocation methodSignature];
    NSLog(@"methodSignature: %@", signature);

//    NSUInteger argumentCount = [signature numberOfArguments];
//    NSLog(@"参数个数: %lu", argumentCount); // 参数至少2个,一个self一个sel
    if (![self.invocationHandler respondsToSelector:invocation.selector]) {
        return;
    }
    
    [invocation setTarget:self.invocationHandler]; // 对象
    [invocation setSelector:@selector(invoke:)]; // 方法
    [invocation invoke];
}

+ (id)createProxyInstance:(id<InvocationHandler>)handler {
    MyProxy *proxy = [MyProxy alloc];
    proxy.invocationHandler = handler;
    return proxy;
}

@end
