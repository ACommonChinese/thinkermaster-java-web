//
//  ViewController.m
//  ProxyDemo
//
//  Created by liuweizhen on 2019/11/2.
//  Copyright © 2019 liuxing8807@126.com. All rights reserved.
//

#import "ViewController.h"
#import "Dog.h"
#import "Cat.h"
#import "DogDao.h"
#import "BMBatis.h"
#import "ONOXMLDocument.h"
#import "IUserDao.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
}

// SqlSessionFactoryBuilder: OK
// SqlSessionFacotry接口 OK
// DefaultSqlSessionFactory OK
- (IBAction)btnClick:(id)sender {
    SqlSessionFactoryBuilder *builder = [[SqlSessionFactoryBuilder alloc] init];
    NSString *configPath = [[NSBundle mainBundle] pathForResource:@"SqlMapConfig" ofType:@"xml"];
    id<SqlSessionFactory> factory = [builder build:configPath]; // DefaultSqlSessionFactory
    id<SqlSession> session = [factory openSession]; // sqlite_open -> DefaultSqlSession
    id<IUserDao> userDao = [session getMapper:@protocol(IUserDao)]; // MapperProxy对象
    // NSArray<User *> *users = [userDao findAll];
    User *user = [userDao getUser:100.0];
//    for (User *user in users) {
//        [user printInfo];
//    }
    NSLog(@"OK");
//    id<DogDao> daoDog = [MyProxy createProxyInstance:@protocol(DogDao) handler:[[MyInvocationHandler alloc] init]];
//    [daoDog selectAll];
}

- (void)testXMLParse {
    NSError *error = nil;
    NSString *path = [[NSBundle mainBundle] pathForResource:@"SqlMapConfig" ofType:@"xml"];
    NSData *xmlData = [NSData dataWithContentsOfFile:path];
    ONOXMLDocument *document = [ONOXMLDocument XMLDocumentWithData:xmlData error:&error];
    /**
    <configuration>
       <mappers>
           <mapper resource="IUserDao.xml" />
       </mappers>
    </configuration>
    */
    [[document rootElement] enumerateElementsWithXPath:@"//mappers/mapper" usingBlock:^(ONOXMLElement * _Nonnull element, NSUInteger idx, BOOL * _Nonnull stop) {
        // NSLog(@"%@", element.attributes);
        // NSLog(@"%@", [[element.attributes allValues] firstObject]);
        NSString *mapperPath = [element valueForAttribute:@"resource"];
        NSLog(@"%@", mapperPath);
        
        // TODO:// 不能直接传mappterPath
        NSDictionary *dict = [self testSubXML:[[NSBundle mainBundle] pathForResource:mapperPath ofType:nil]];
        // NSLog(@"%@", [self testSubXML]);
    }];
}

- (NSMutableDictionary<NSString *, Mapper *> *)testSubXML:(NSString *)mapperPath {
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
    
    // <select>...</select>
    [root enumerateElementsWithXPath:@"//select" usingBlock:^(ONOXMLElement * _Nonnull element, NSUInteger idx, BOOL * _Nonnull stop) {
        NSString *id = [element valueForAttribute:@"id"];
        NSString *resultType = [element valueForAttribute:@"resultType"];
        NSString *queryString = [element stringValue];
        NSString *key = [NSString stringWithFormat:@"%@.%@", namespace, id];
        Mapper *mapper = Mapper.new;
        mapper.queryString = queryString;
        mapper.resultType = resultType;
        NSLog(@"key: %@", key);
        NSLog(@"queryString: %@", queryString);
        NSLog(@"resultType: %@", resultType);
        [mappers setObject:mapper forKey:key];
    }];
    return mappers;
}

@end
