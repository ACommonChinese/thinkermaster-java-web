//
//  XMLConfigBuilder.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Configuration.h"

@interface XMLConfigBuilder : NSObject

+ (Configuration *)loadConfiguration:(NSString *)path;

@end
