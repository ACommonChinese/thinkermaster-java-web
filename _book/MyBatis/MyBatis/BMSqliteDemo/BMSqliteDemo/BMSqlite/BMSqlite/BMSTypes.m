//
//  BMSTypes.m
//  BMSqlite
//
//  Created by banma-1118 on 2019/11/12.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import "BMSTypes.h"

@interface BMSType ()
//
//TEXT
//NUMERIC
//INTEGER
//REAL
//BLOB
- (instancetype)initWithEncodeType:(char *)encodeType ocType:(NSString *)ocType sqlType:(NSString *)sqlType;

@end

@implementation BMSType

- (instancetype)initWithEncodeType:(char *)encodeType ocType:(NSString *)ocType sqlType:(NSString *)sqlType; {
    BMSType *typeObj = [[BMSType alloc] init];
    typeObj.encodeType = encodeType;
    typeObj.ocType = ocType;
    typeObj.sqlType = sqlType;
    return typeObj;
}

@end

//static BMSType *t_blob;
//static BMSType *t_blob64;
//static BMSType *t_double;
//static BMSType *t_int;
//static BMSType *t_int64;
//static BMSType *t_null;
//static BMSType *t_text;
//static BMSType *t_text16;
//static BMSType *t_text64;
//static BMSType *t_value;
//static BMSType *t_pointer;
//static BMSType *t_zeroblob;
//static BMSType *t_zeroblob64;

@implementation BMSTypes

+ (BMSType *)_blob {
    return nil;
}

+ (BMSType *)_blob64 {
    return nil;
}

+ (BMSType *)_double {
    static BMSType *typeDouble;
    if (typeDouble == nil) {
        typeDouble = [[BMSType alloc] initWithEncodeType:@encode(double) ocType:@"double" sqlType:@"double"];
    }
    return typeDouble;
}

+ (BMSType *)_int {
    static BMSType *typeInt;
    if (typeInt == nil) {
        typeInt = [[BMSType alloc] initWithEncodeType:@encode(int) ocType:@"int" sqlType:@"int"];
    }
    return typeInt;
}

//+ (BMSType *)_int {
//    if (nil == __int) {
//        __int = [[BMSType alloc] initWithEncodeType:<#(char *)#> ocType:<#(NSString *)#> sqlType:<#(NSString *)#>];
//    }
//}

//@property (nonatomic, class, readonly) BMSType *_int;
///// sqlite3_bind_int64
//@property (nonatomic, class, readonly) BMSType *_int64;
///// sqlite3_bind_text
//@property (nonatomic, class, readonly) BMSType *_text;
///// sqlite3_bind_text64
//@property (nonatomic, class, readonly) BMSType *_text64;
//
///// TODO:// Not used sqlite3_bind_value
//@property (nonatomic, class, readonly) BMSType *_value;
///// TODO:// Not used sqlite3_bind_pointer
//@property (nonatomic, class, readonly) BMSType *_pointer;
///// TODO:// Not used sqlite3_bind_zeroblob
//@property (nonatomic, class, readonly) BMSType *_zeroblob;
///// TODO:// Not used sqlite3_bind_zeroblob64
//@property (nonatomic, class, readonly) BMSType *_zeroblob64;

@end
