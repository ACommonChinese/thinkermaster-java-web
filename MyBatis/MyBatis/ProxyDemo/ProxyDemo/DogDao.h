//
//  DogDao.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/2.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@protocol DogDao <NSObject>

- (void)selectAll;

@end

NS_ASSUME_NONNULL_END
