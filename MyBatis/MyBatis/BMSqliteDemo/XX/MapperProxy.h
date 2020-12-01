//
//  MapperProxy.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "InvocationHandler.h"
#import "Mapper.h"

NS_ASSUME_NONNULL_BEGIN

@interface MapperProxy : NSObject <InvocationHandler>

@property (nonatomic, strong) Protocol *protocol;
@property (nonatomic, strong) NSDictionary<NSString *, Mapper *> *mappers;

@end

NS_ASSUME_NONNULL_END
