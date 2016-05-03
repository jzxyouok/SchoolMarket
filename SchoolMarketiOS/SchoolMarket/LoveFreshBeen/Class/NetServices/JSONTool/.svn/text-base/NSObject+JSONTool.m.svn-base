//
//  NSObject+JSONTool.m
//  LCFoundationLib
//
//  Created by HuZeSen on 15/7/27.
//  Copyright (c) 2015年 GZLC. All rights reserved.
//

#import "NSObject+JSONTool.h"

@implementation NSObject (JSONTool)

+ (NSString *)jsonStringWithNSDictionary:(NSDictionary *)dic
{
    if ([NSJSONSerialization isValidJSONObject:dic]) {
        NSError *error;
        NSData *jsonData = [NSJSONSerialization dataWithJSONObject:dic options:NSJSONWritingPrettyPrinted error:&error];
        if (error)
        {
            NSLog(@"字典转为JSON串,转换失败");
        }
        NSString *jsonString = [[NSMutableString alloc] initWithData:jsonData encoding:NSUTF8StringEncoding];
        return jsonString;
    }
    
    return nil;
}

+ (NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString
{
    if (jsonString.length==0) {
        return nil;
    }
    NSData *data = [jsonString dataUsingEncoding:NSUTF8StringEncoding];
    NSError *error;
    NSDictionary *dic = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingAllowFragments error:&error];
    if ([NSJSONSerialization isValidJSONObject:dic]) {
        return dic;
    }
    return nil;
}

+ (NSString *)jsonStringWithNSArray:(NSArray *)array
{
    if ([NSJSONSerialization isValidJSONObject:array])
    {
        NSError *error;
        NSData *data = [NSJSONSerialization dataWithJSONObject:array options:0 error:&error];
        return [[NSString alloc]initWithData:data encoding:NSUTF8StringEncoding];
    }
    return nil;
}
@end
