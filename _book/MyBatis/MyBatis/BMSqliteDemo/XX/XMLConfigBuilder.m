//
//  XMLConfigBuilder.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/3.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "XMLConfigBuilder.h"
#import "ONOXMLDocument.h"

@implementation XMLConfigBuilder

// TODO:// Bundle path
// 这里传入的path需要是带Bundle的path
+ (Configuration *)loadConfiguration:(NSString *)path {
    if (path.length <= 0) {
        return nil;
    }
    Configuration *cfg = [[Configuration alloc] init];
    NSError *error = nil;
    NSData *xmlData = [NSData dataWithContentsOfFile:path];
    ONOXMLDocument *document = [ONOXMLDocument XMLDocumentWithData:xmlData error:&error];
    if (!document || error) {
        NSLog(@"error in read xml data: %@", error);
        return nil;
    }
    /**
     <configuration>
         <mappers>
             <mapper resource="IUserDao.xml" />
         </mappers>
     </configuration>
     */
    [[document rootElement] enumerateElementsWithXPath:@"//mappers/mappter" usingBlock:^(ONOXMLElement * _Nonnull element, NSUInteger idx, BOOL * _Nonnull stop) {
        NSString *mapperPath = [element valueForAttribute:@"resource"];
        // TODO:// Bundle path
        NSMutableDictionary<NSString *, Mapper *> *mappers = [self loadMapperConfiguration:[[NSBundle mainBundle] pathForResource:mapperPath ofType:nil]];
        [cfg setMappers:mappers];
    }];
    return cfg;
}

+ (NSMutableDictionary<NSString *, Mapper *> *)loadMapperConfiguration:(NSString *)mapperPath {
    NSMutableDictionary<NSString *, Mapper *> *mappers = [[NSMutableDictionary alloc] init];
    NSError *error = nil;
    NSData *xmlData = [NSData dataWithContentsOfFile:mapperPath];
    ONOXMLDocument *document = [ONOXMLDocument XMLDocumentWithData:xmlData error:&error];
    if (!document || error) {
        NSLog(@"error in load mapper configuration: %@", error);
        return nil;
    }
    ONOXMLElement *root = document.rootElement;
    NSString *namespace = [root valueForAttribute:@"namespace"];
    [root enumerateElementsWithXPath:@"//select" usingBlock:^(ONOXMLElement * _Nonnull element, NSUInteger idx, BOOL * _Nonnull stop) {
        NSString *id = [element valueForAttribute:@"id"];
        NSString *resultType = [element valueForAttribute:@"resultType"];
        NSString *queryString = [element stringValue];
        NSString *key = [NSString stringWithFormat:@"%@.%@", namespace, id];
        Mapper *mapper = Mapper.new;
        mapper.queryString = queryString;
        mapper.resultType = resultType;
        [mappers setObject:mapper forKey:key];
    }];
    return mappers;
}

@end
