//
//  NSString+MD5Tool.m
//  LCFoundationLib
//
//  Created by HuZeSen on 15/7/27.
//  Copyright (c) 2015年 GZLC. All rights reserved.
//

#import "NSString+MD5Tool.h"
#import <CommonCrypto/CommonDigest.h>

@implementation NSString (MD5Tool)

- (NSString *)encryptMD5String
{
    const char *original_str = [self UTF8String];
    unsigned char result[CC_MD5_DIGEST_LENGTH];
    CC_MD5(original_str, (u_int32_t)strlen(original_str), result);
    NSMutableString *hash = [NSMutableString string];
    for (int i = 0; i < 16; i++)
        [hash appendFormat:@"%02X", result[i]];
    return [hash lowercaseString];
}
@end
