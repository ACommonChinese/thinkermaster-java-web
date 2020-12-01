//
//  BMSTypes.h
//  BMSqlite
//
//  Created by banma-1118 on 2019/11/12.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface BMSType : NSObject

@property (nonatomic, assign) char *encodeType;
@property (nonatomic, copy) NSString *ocType;
@property (nonatomic, copy) NSString *sqlType;

@end

@interface BMSTypes : NSObject

/// TODO:// Not used sqlite3_bind_blob
@property (nonatomic, class, readonly) BMSType *_blob;
/// TODO:// Not used sqlite3_bind_blob_64
@property (nonatomic, class, readonly) BMSType *_blob64;

/// sqlite3_bind_double
@property (nonatomic, class, readonly) BMSType *_double;
/// sqlite3_bind_int
@property (nonatomic, class, readonly) BMSType *_int;
/// sqlite3_bind_int64
@property (nonatomic, class, readonly) BMSType *_int64;
/// sqlite3_bind_text
@property (nonatomic, class, readonly) BMSType *_text;
/// sqlite3_bind_text64
@property (nonatomic, class, readonly) BMSType *_text64;

/// TODO:// Not used sqlite3_bind_value
@property (nonatomic, class, readonly) BMSType *_value;
/// TODO:// Not used sqlite3_bind_pointer
@property (nonatomic, class, readonly) BMSType *_pointer;
/// TODO:// Not used sqlite3_bind_zeroblob
@property (nonatomic, class, readonly) BMSType *_zeroblob;
/// TODO:// Not used sqlite3_bind_zeroblob64
@property (nonatomic, class, readonly) BMSType *_zeroblob64;

@end

NS_ASSUME_NONNULL_END
