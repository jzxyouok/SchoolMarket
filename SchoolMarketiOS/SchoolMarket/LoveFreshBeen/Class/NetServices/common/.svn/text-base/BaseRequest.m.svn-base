//
//  BaseRequest.m
//  fish
//
//  Created by HuZeSen on 15/12/1.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import "BaseRequest.h"
//#import "LCNetworkConfig.h"
#import "NSString+MD5Tool.h"
#import "TimeClock.h"
#import "NSObject+JSONTool.h"

static NSString *const App_Key = @"1261827190";
static NSString *const App_Secret_Key = @"7dd7561a9b7a9abff9ae2bd4550420f2";

@implementation BaseRequest
{
    NSInteger timeStamp;
    id _argument;
}

- (instancetype)initWithArgument:(id)argument
{
    self = [super init];
    if (self) {
        _argument = [argument copy];
    }
    return self;
}

- (NSString *)requestUrl {
    return @"";
}

- (NSTimeInterval)requestTimeoutInterval
{
    return 5;
}

- (YTKRequestMethod)requestMethod {
    return YTKRequestMethodPost;
}

- (id)responseJSONObject
{
    id object = [super responseJSONObject];
    if (object) {
        return object;
    }
    return [NSString dictionaryWithJsonString:[super responseString]];
}

- (NSDictionary *)requestHeaderFieldValueDictionary
{
    NSInteger interval = [self getTimeStamp];
    NSString *oriStr = [App_Key stringByAppendingFormat:@"%ld%@",(long)interval,App_Secret_Key];
    NSString *time = [NSString stringWithFormat:@"%ld",(long)interval];
    return @{@"LC-Appkey":App_Key,
             @"LC-Timestamp":time,
             @"LC-Sign":[oriStr encryptMD5String],
             @"LC-ClientVersion":[[[NSBundle mainBundle]infoDictionary]objectForKey:@"CFBundleShortVersionString"],
             @"LC-ClientBrand":@"iPhone",
             @"LC-ClientOSVersion":@([[[UIDevice currentDevice]systemVersion]floatValue]).stringValue
             };
}

- (id)requestArgument
{
    return _argument;
}

#pragma mark- send request
- (void)startWithSuccess:(RequestSuccessfulBlock)sBlock failure:(RequestFailedBlock)fBlock
{
    [self startWithCompletionBlockWithSuccess:^(YTKBaseRequest *request) {
        !sBlock?:sBlock(request);
    } failure:^(YTKBaseRequest *request) {
        !fBlock?:fBlock(request);
    }];
}
#pragma mark- method

//- (NSString *)identify
//{
//    return @"";
//}

- (NSInteger)getTimeStamp
{
    return (NSInteger)[[TimeClock sharedInstance]getTimeInterval];
}

@end
