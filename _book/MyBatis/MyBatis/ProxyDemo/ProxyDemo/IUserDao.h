//
//  IUserDao.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "User.h"

NS_ASSUME_NONNULL_BEGIN

@protocol IUserDao <NSObject>

- (NSArray<User *> *)findAll;
- (User *)getUser:(float)score;

@end

NS_ASSUME_NONNULL_END
