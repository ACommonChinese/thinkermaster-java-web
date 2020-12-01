//
//  BMProxy.m
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//  

#import "BMProxy.h"
#import <objc/runtime.h>

@interface BMProxy ()

@property (nonatomic, strong) Protocol *innerObject;

@end

@implementation BMProxy

- (void)dealloc {
    NSLog(@"did dealloc!");
    [super dealloc];
}

- (id)initWithObject:(id)obj {
    self.innerObject = obj;
    return self;
}

+ (id)proxyWithObject:(Protocol *)object {
    BMProxy *proxy = [BMProxy alloc];
    proxy.innerObject = object;
    return proxy;
}

//+ (id)proxyWithObj:(id)object {
//    __strong BMProxy *proxy = [BMProxy alloc];
//    // 持有要 hook 的协议对象
//    proxy.innerObject = object;
//    // 注意返回的值是 Proxy 对象
//    return proxy;
//}

- (NSMethodSignature *)methodSignatureForSelector:(SEL)sel {
    // 这里可以返回任何 NSMethodSignature 对象，也可以完全自己构造一个
    return [self methodSignatureFromProtocol:_innerObject selector:sel];
}

- (NSMethodSignature *)methodSignatureFromProtocol:(Protocol *)protocol selector:(SEL)aSelector {
    if (aSelector == NULL) {
        return nil;
    }
    unsigned int outCount = 0;
    struct objc_method_description *aList = protocol_copyMethodDescriptionList(protocol, YES, YES, &outCount);
    NSLog(@"方法个数：%u", outCount);
    if (aList != NULL && outCount > 0) {
        for (unsigned int index = 0; index < outCount; index++) {
            struct objc_method_description methodDesc = aList[index];
            SEL aProtocolSelector = methodDesc.name;
            if (aSelector == aProtocolSelector) {
                char *aType = methodDesc.types;
                NSLog(@"%@", [[NSString alloc] initWithUTF8String:aType]);
                if (aType != NULL) {
                    free(aList);
                    return [NSMethodSignature signatureWithObjCTypes:aType];
                }
            }
        }
    }
    free(aList);
    return nil;
}

- (void)forwardInvocation:(NSInvocation *)anInvocation {
    NSInvocation *invocation = anInvocation;
    NSString *selectorName = NSStringFromSelector(invocation.selector);
    NSLog(@"Before calling %@",selectorName);
    [invocation retainArguments];
    NSMethodSignature *sig = [invocation methodSignature];
    // 获取参数个数，注意在本例里这里的值是 3，因为 objc_msgSend 隐含了 self、selector 参数
    // - (NSString *)barking:(NSInteger)months
    NSUInteger cnt = [sig numberOfArguments];
    // 本例只是简单的将参数和返回值打印出来
    for (int i = 0; i < cnt; i++) {
        // 参数类型
        const char * type = [sig getArgumentTypeAtIndex:i];
        // https://stackoverflow.com/questions/8672675/why-does-the-arc-migrator-say-that-nsinvocations-setargument-is-not-safe-unle/8672993
        // 如果invocation不置空，会引发下面的警告甚至是Crash
        // BMProxy object 0x103202540 overreleased while already deallocating; break on objc_overrelease_during_dealloc_error to debug
        if(strcmp(type, "@") == 0){
            NSObject *obj;
            [invocation getArgument:&obj atIndex:i];
            // 这里输出的是："parameter (0)'class is MyProxy"，也证明了这是 objc_msgSend 的第一个参数
            NSLog(@"parameter (%d)'class is %@", i, [obj class]);
        }
        else if(strcmp(type, ":") == 0){
            SEL sel;
            [invocation getArgument:&sel atIndex:i];
            // 这里输出的是:"parameter (1) is barking:"，也就是 objc_msgSend 的第二个参数
            NSLog(@"parameter (%d) is %@", i, NSStringFromSelector(sel));
        }
        else if(strcmp(type, "q") == 0){
            int arg = 0;
            [invocation getArgument:&arg atIndex:i];
            // 这里输出的是:"parameter (2) is int value is 4"，稍后会看到我们在调用 barking 的时候传递的参数就是 4
            NSLog(@"parameter (%d) is int value is %d", i, arg);
        }
        NSLog(@"END========");
    }
    
    
    // overreleased while already deallocating; break on objc_overrelease_during_dealloc_error to debug
    // [invocation invokeWithTarget:nil];
    
    // 消息转发
    // 不可invoke，因为_innerObject并不是一个普通对象，而j是一个协议对象
//    [invocation invokeWithTarget:nil];
    //  const char *retType = [sig methodReturnType];
    //  if(strcmp(retType, "@") == 0){
    //      NSObject *ret;
    //      [invocation getReturnValue:&ret];
    //      //这里输出的是:"return value is wang wang!"
    //      NSLog(@"return value is %@", ret);
    //  }
    // NSLog(@"After calling %@", selectorName);
}

//- (void)forwardInvocation:(NSInvocation *)invocation {
//    if([_innerObject respondsToSelector:invocation.selector]){
//        NSString *selectorName = NSStringFromSelector(invocation.selector);
//        NSLog(@"Before calling %@",selectorName);
//        [invocation retainArguments];
//        NSMethodSignature *sig = [invocation methodSignature];
//        // 获取参数个数，注意在本例里这里的值是 3，因为 objc_msgSend 隐含了 self、selector 参数
//        NSUInteger cnt = [sig numberOfArguments];
//        // 本例只是简单的将参数和返回值打印出来
//        for (int i = 0; i < cnt; i++) {
//            // 参数类型
//            const char * type = [sig getArgumentTypeAtIndex:i];
//            if(strcmp(type, "@") == 0){
//                NSObject *obj;
//                [invocation getArgument:&obj atIndex:i];
//                // 这里输出的是："parameter (0)'class is MyProxy"，也证明了这是 objc_msgSend 的第一个参数
//                NSLog(@"parameter (%d)'class is %@", i, [obj class]);
//            }
//            else if(strcmp(type, ":") == 0){
//                SEL sel;
//                [invocation getArgument:&sel atIndex:i];
//                // 这里输出的是:"parameter (1) is barking:"，也就是 objc_msgSend 的第二个参数
//                NSLog(@"parameter (%d) is %@", i, NSStringFromSelector(sel));
//            }
//            else if(strcmp(type, "q") == 0){
//                int arg = 0;
//                [invocation getArgument:&arg atIndex:i];
//                // 这里输出的是:"parameter (2) is int value is 4"，稍后会看到我们在调用 barking 的时候传递的参数就是 4
//                NSLog(@"parameter (%d) is int value is %d", i, arg);
//            }
//        }
//        // 消息转发
//        [invocation invokeWithTarget:_innerObject];
//        const char *retType = [sig methodReturnType];
//        if(strcmp(retType, "@") == 0){
//            NSObject *ret;
//            [invocation getReturnValue:&ret];
//            //这里输出的是:"return value is wang wang!"
//            NSLog(@"return value is %@", ret);
//        }
//        NSLog(@"After calling %@", selectorName);
//    }
//}

@end
