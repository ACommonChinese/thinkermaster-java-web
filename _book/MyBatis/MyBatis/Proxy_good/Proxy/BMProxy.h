//
//  BMProxy.h
//  Proxy
//
//  Created by liuxing8807@126.com on 2019/11/6.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BMProxy : NSProxy

- (id)initWithObject:(Protocol *)object;

@end

