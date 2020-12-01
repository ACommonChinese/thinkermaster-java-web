//
//  main.m
//  encode
//
//  Created by banma-1118 on 2019/11/12.
//  Copyright © 2019 liuweizhen. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <objc/runtime.h>

int main(int argc, const char * argv[]) {
    @autoreleasepool {
        char *type = @encode(int);
        printf("%s\n", type);
        if (strcmp(type, @encode(int)) == 0) {
            printf("Yes, equal!\n");
        }
    }
    return 0;
}
