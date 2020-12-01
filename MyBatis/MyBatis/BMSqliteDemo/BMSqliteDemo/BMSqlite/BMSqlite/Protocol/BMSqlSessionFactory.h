//
//  BMSqlSessionFactory.h
//  BMSqlite
//
//  Created by banma-1118 on 2019/11/15.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "BMSqlSession.h"

NS_ASSUME_NONNULL_BEGIN

@protocol BMSqlSessionFactory <NSObject>

/**
 创建一个新的数据库操作对象
 */
- (id<BMSqlSession>)openSession;

@end

NS_ASSUME_NONNULL_END
