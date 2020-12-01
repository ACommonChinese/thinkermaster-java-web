//
//  Dog.h
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol Barking <NSObject>

- (NSString *)barking:(NSInteger)months;

@end

@interface Dog : NSObject <Barking>

@end
