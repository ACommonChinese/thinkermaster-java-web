//
//  BMDefaultSqlSessionFactory.m
//  BMSqlite
//
//  Created by banma-1118 on 2019/11/15.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import "BMDefaultSqlSessionFactory.h"
#import "BMDefaultSqlSession.h"

@implementation BMDefaultSqlSessionFactory

- (id<BMSqlSession>)openSession {
    return [[BMDefaultSqlSession alloc] init];
}

@end
