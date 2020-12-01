//
//  NSMethodSignature+RuntimeAdditions.h
//  Proxy
//
//  Created by banma-1118 on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <AppKit/AppKit.h>


#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface NSMethodSignature (RuntimeAdditions)

- (NSMethodSignature *)methodSignatureForSelector:(SEL)aSelector;

@end

NS_ASSUME_NONNULL_END
