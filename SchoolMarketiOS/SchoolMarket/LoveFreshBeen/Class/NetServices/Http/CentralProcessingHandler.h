//
//  CentralProcessingHandler.h
//  fish
//
//  Created by HuZeSen on 15/12/2.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import <Foundation/Foundation.h>
//#import "UploadFileRequest.h"
//#import "UpdateFaceItems.h"
#import "BaseVerificationModel.h"
@class YTKChainRequest;
@class YTKBaseRequest;
//@protocol ChainRequestDelegate;

/**
 *  如果需要缓存返回的数据,会执行这个回调
 *
 *  @param responseObject 返回的model数组
 *  @param timeStamp      时间戳,目前未设置
 *  @param responseData   服务器返回的数据
 */
typedef void(^SuccessfulCacheBlock)(NSArray *responseObject,double timeStamp,NSArray <NSDictionary *>* responseData);

typedef void(^SuccessfulBlock)(NSArray *responseObject,double timeStamp); // 暂时没有返回 timeStamp
typedef void(^FailedBlock)(NSString *errDescription, NSInteger errCode);


typedef void(^ResponseBlock)(BOOL error,NSString *errDescription,NSInteger errCode,NSDictionary *dicWhenSuccess);
typedef void(^BoolResultBlock)(BOOL result);

#define ResponseCodeKey @"ret_code"
#define ResponseTimeStampKey @"timestamp"
#define ResponseDataKey @"data"
#define ResponseIdentifyKey @"identifier"

extern NSString *ResetUnreadNotifyCount;
/**
 *  此类提供2种请求方式
 *  一种是普通的请求,可多个请求体一次发.比如同时获取首页广告,列表数据等.返回model数组
 *  一种是链式请求(chainRequest),有先后依赖关系顺序的请求,比如检测某账号是否被注册,如果未注册,则发送注册请求
 *  如果链式请求中下一个请求需要上一个请求返回的数据,暂时未做此功能
 *  如果在成功或者失败回调里增加了别的网络请求,请先调用cleanCache方法
 */

@interface CentralProcessingHandler : NSObject

//@property (nonatomic, weak) id<ChainRequestDelegate> delegate;
@property (nonatomic) NSInteger unreadNotifyCount;
//@property (nonatomic) AFNetworkReachabilityStatus reachabilityStatus;

+ (instancetype)sharedInstance;

//#pragma mark- 传递参数的方法,不建议使用了
///**
// *  单个普通请求时,调用此方法增加参数,param可以为nil.
// *  @warning 手动调用sendRequestWithSuccessBlock:FaliedBlock:前,不要多次调用这个方法.
// *                    如果想合并多个请求,请调用另一个方法.
// */
//- (void)addRequestIdentify:(NSString *)identify ParamsDic:(NSDictionary *)param ModelName:(NSString *)name;
//
///**
// *  合并多个普通请求时,调用此方法增加参数,params可以为nil,自动补充为@[@{},@{},...]
// */
//- (void)addRequestIdentifiers:(NSArray<NSString *> *)identifiers ParamsDics:(NSArray<NSDictionary *> *)params ModelName:(NSArray<NSString *> *)name;

#pragma mark- 传递参数的方法,建议使用,节省代码量
/**
 *  传单个参数后,自动发送请求
 *  param可以为nil,自动补充为@{}
 *
 */
+ (void)sendRequestWithIdentify:(NSString *)identify Param:(NSDictionary *)param modelName:(NSString *)name SuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock;

/**
 *  传多个参数后,自动发送请求
 *  param可以为nil,自动补充为@[@{},@{},...]
 *
 */
+ (void)sendRequestWithIdentifiersArr:(NSArray<NSString *>*)identifiers ParamsArr:(NSArray<NSDictionary *>*)params modelNameArr:(NSArray<NSString *>*)nameArr SuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock;

/**
 *  链式请求时,调用此方法增加参数.param可以为nil
 */
- (void)addChainRequestIdentify:(NSString *)identify ChainParamsDic:(NSDictionary *)param ChainModelName:(NSString *)name;

#pragma mark- 传递完参数,手动调用发送请求的方法
/**
 *  普通请求,添加完数据,调用此方法发送请求,无需缓存服务器返回的数据
 */
- (void)sendRequestWithSuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock;

/**
 *  普通请求,添加完数据,调用此方法发送请求,缓存服务器返回的数据
 *  可以替代上一个方法
 *
 */
- (void)sendCacheRequestWithSuccessCacheBlock:(SuccessfulCacheBlock)sBlock FaliedBlock:(FailedBlock)fBlock;

/**
 *  链式请求,添加完数据,调用此方法发送请求
 */
- (void)sendChainRequestWithSuccessfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock;

#pragma mark- 通知数量相关
/**
 *  给未读通知数 + 1
 */
- (void)addNotifyUnreadCount;

/**
 *  清零未读数
 */
- (void)resetNotifyUnreadCount;

#pragma mark- 上传相关
///**
// *  上传头像
// *
// *  @param image  头像
// *  @param name   model 名字
// */
//+ (void)uploadAvatarWithFile:(UIImage *)image modelName:(NSString *)name successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock;
//
///**
// *  上传图片
// *
// *  @param image  图片
// *  @param type   图片类型
// *  @param o_id   如果是头像,则传0
// *  @param index  如果是头像,传1
// */
//+ (void)uploadFileWithFile:(UIImage *)image fileType:(UploadFileType)type ownerId:(NSInteger)o_id fileIndex:(NSInteger)index successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock;

/**
 *  上传视频
 *
 *  @param videoURL 本地视频路径
 *  @param o_id     <#o_id description#>
 *  @param sBlock   <#sBlock description#>
 *  @param fBlock   <#fBlock description#>
 */
+ (void)uploadVideoWithFileURLString:(NSString *)videoURL ownerId:(NSInteger)o_id successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock;

#pragma mark- 重发非正常队列的相关方法
/**
 *  程序刚启动时的一些初始操作
 *  目前只是添加了网络监听,并且会检测是否有未发送的缓存请求
 *  网络状况一旦变更,只要不是无网络状态,则会检测发送失败的队列,重新发送请求
 */
+ (void)launchingOpration;

/**
 *  重发之前发送失败的请求,但仍能继续被服务器接收的
 */
+ (void)resendCacheArray;

@end

//#pragma mark- 链式请求的代理
//@protocol ChainRequestDelegate <NSObject>
//
//@optional
//
//- (void)chainRequestDataArray:(NSArray *)dataArray;
//- (void)chainRequestFailed:(YTKChainRequest *)chainRequest failedBaseRequest:(YTKBaseRequest*)request;
//
//@end
