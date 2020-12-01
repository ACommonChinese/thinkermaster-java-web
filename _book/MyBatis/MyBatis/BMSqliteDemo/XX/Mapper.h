//
//  Mapper.h
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

/**
* 用于封装执行的SQL语句和结果类型的全限定类名, 对应:
*  <mapper namespace="com.daliu.dao.IUserDao">
       <!--配置查询所有-->
       <select id="findAll" resultType="com.daliu.domain.User">
           select * from user
       </select>
   </mapper>
*/
@interface Mapper : NSObject

@property (nonatomic, copy) NSString *queryString;
@property (nonatomic, copy) NSString *resultType;

@end

NS_ASSUME_NONNULL_END
