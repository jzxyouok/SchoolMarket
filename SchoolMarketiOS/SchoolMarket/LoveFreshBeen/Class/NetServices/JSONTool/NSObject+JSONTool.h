//
//  NSObject+JSONTool.h
//  LCFoundationLib
//
//  Created by HuZeSen on 15/7/27.
//  Copyright (c) 2015年 GZLC. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSObject (JSONTool)

/**
 *  将字典中转为JSON串
 */
+ (NSString *)jsonStringWithNSDictionary:(NSDictionary *)dic;

/**
 *  将JSON串转为字典
 */
+ (NSDictionary *)dictionaryWithJsonString:(NSString *)jsonString;

/**
 * 将数组转为JSON串
 */
+ (NSString *)jsonStringWithNSArray:(NSArray *)array;

@end
