//
//  LCNetworkConfig.h
//  fish
//
//  Created by J on 15/12/1.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import <Foundation/Foundation.h>

typedef NS_ENUM(NSInteger, PostType)
{
    PostTypeResponse = 0,            // 发送类型为响应
    PostTypeRequest = 1               // 发送类型为请求
};

@interface LCNetworkConfig : NSObject

@property (nonatomic, copy, readonly) NSArray<NSNumber *> *timeStampArray;

+ (instancetype)sharedInstance;


//- (void)clearLastCacheItem;
///**
// *  清理缓存下来的请求数据
// */
//- (void)clearCache;

+ (void)setupNetworkConfig;

//+ (void)setupUpLoadUrlWithFileType:(UploadFileType)fileType ownerId:(NSInteger)o_id fileIndex:(NSInteger)index;

- (NSDictionary *)setupRequestConfigWithIdentifiersArray:(NSArray *)identifiers ParamDicArray:(NSArray *)paramsArr PostType:(PostType)type;

@end
