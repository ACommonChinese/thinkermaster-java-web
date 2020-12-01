//
//  Animal.h
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol Animal <NSObject>

- (void)eat:(NSString *)food;
- (void)drink:(NSString *)water;
- (void)think;

@end

NS_ASSUME_NONNULL_END
