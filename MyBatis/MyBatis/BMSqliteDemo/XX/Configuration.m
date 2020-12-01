//
//  Configuration.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "Configuration.h"

@implementation Configuration

- (void)setMappers:(NSMutableDictionary<NSString *,Mapper *> *)mappers {
    if (!mappers) {
        return;
    }
    [self.mappers addEntriesFromDictionary:mappers];
}

@end
