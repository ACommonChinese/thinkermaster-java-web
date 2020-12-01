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
    NSLog(@"BMProxy did dealloc!");
    [super dealloc];
}

- (id)initWithProtocol:(id)obj {
    self.innerObject = obj;
    return self;
}

+ (id)proxyWithObject:(Protocol *)object {
    BMProxy *proxy = [BMProxy alloc];
    proxy.innerObject = object;
    return [proxy autorelease];
}

- (NSMethodSignature *)methodSignatureForSelector:(SEL)sel {
    return [self methodSignatureFromProtocol:_innerObject selector:sel];
}

- (NSMethodSignature *)methodSignatureFromProtocol:(Protocol *)protocol selector:(SEL)aSelector {
    if (aSelector == NULL) {
        return nil;
    }
    unsigned int outCount = 0;
    struct objc_method_description *aList = protocol_copyMethodDescriptionList(protocol, YES, YES, &outCount);
    if (aList != NULL && outCount > 0) {
        for (unsigned int index = 0; index < outCount; index++) {
            struct objc_method_description methodDesc = aList[index];
            SEL aProtocolSelector = methodDesc.name;
            if (aSelector == aProtocolSelector) {
                char *aType = methodDesc.types;
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
    // NSString *selectorName = NSStringFromSelector(invocation.selector);
    [invocation retainArguments];
    NSMethodSignature *sig = [invocation methodSignature];
    NSUInteger cnt = [sig numberOfArguments];
    for (int i = 2; i < cnt; i++) {
        const char * type = [sig getArgumentTypeAtIndex:i];
        if(strcmp(type, "@") == 0){
            NSObject *obj;
            [invocation getArgument:&obj atIndex:i];
            NSLog(@"参数: %@", obj);
        }
        else if(strcmp(type, "q") == 0){
            int arg = 0;
            [invocation getArgument:&arg atIndex:i];
            NSLog(@"参数: %d", arg);
        }
    }
    
    // 消息转发
    // 不可invoke，因为_innerObject并不是一个普通对象，而j是一个协议对象
    //  [invocation invokeWithTarget:nil];
    //  const char *retType = [sig methodReturnType];
    //  if(strcmp(retType, "@") == 0){
    //      NSObject *ret;
    //      [invocation getReturnValue:&ret];
    //      //这里输出的是:"return value is wang wang!"
    //      NSLog(@"return value is %@", ret);
    //  }
}

- (void)invoke {
    
}

@end
