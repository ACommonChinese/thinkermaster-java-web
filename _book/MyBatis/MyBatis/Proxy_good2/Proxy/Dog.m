//
//  Dog.m
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import "Dog.h"

@implementation Dog

- (NSString *)barking:(NSInteger)months {
    return months > 3 ? @"wang wang!" : @"eng eng!";
}

@end
