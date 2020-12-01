//
//  Configuration.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Mapper.h"

NS_ASSUME_NONNULL_BEGIN

/**
 key: com.daliu.dao.IUserDao.findAll (命名空间+类名+方法名)
 value: mapper对象 {
    queryString: SQL语句
    resultType: 返回类型(一行对应的Model)
 }
 */
@interface Configuration : NSObject

@property (nonatomic, copy) NSString *dbPath;
@property (nonatomic, strong) NSMutableDictionary<NSString *, Mapper *> *mappers;

@end

NS_ASSUME_NONNULL_END
