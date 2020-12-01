//
//  main.m
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//  https://www.dzliving.com/2019/09/16/iOS/iOS%E5%8E%9F%E7%90%86/NSProxy/

#import <Foundation/Foundation.h>
#import "Dog.h"
#import "BMProxy.h"
#import "MyProxy.h"
#import "Animal.h"

void testBMProxy() {
    id<Barking> dog = (id<Barking>)[[BMProxy alloc] initWithObject:@protocol(Barking)];
    [dog barking:10];
}

void testBMProxy2() {
//    id<Animal> animal = [BMProxy proxyWithObj:@protocol(Animal)];
//    [animal eat:@"肉"];
//    [animal drink:@"西瓜汁"];
//    [animal think];
}

int main(int argc, const char * argv[]) {
    @autoreleasepool {
         testBMProxy();
//        testBMProxy2();
        NSLog(@"End");
    }
    return 0;
}
