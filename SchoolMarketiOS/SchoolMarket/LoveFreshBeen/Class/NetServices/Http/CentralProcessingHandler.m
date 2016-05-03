//
//  CentralProcessingHandler.m
//  fish
//
//  Created by HuZeSen on 15/12/2.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#define WEAKSELF typeof(self) __weak weakSelf = self;

#import "CentralProcessingHandler.h"
#import "BaseRequest.h"
#import "LCNetworkConfig.h"
#import "TimeClock.h"
#import "NSObject+JSONTool.h"
#import "YTKChainRequest.h"
#import "MJExtension.h"
#import "BaseVerificationModel.h"
//#import "PostHandler.h"
#import "LCAccount.h"
//#import "AppDelegate.h"

static NSString *notifyString;
NSString *ResetUnreadNotifyCount = @"resetUnreadNotifyCount";

#define ID_CACHE_KEY            @"identify"
#define MODEL_CACHE_KEY   @"model"
#define PARAM_CACHE_KEY   @""

#define TimeStampErrCode 300
typedef NS_ENUM(NSInteger, RequestType)
{
    RequestTypeNormal = 0,
    RequestTypeChain
};
@interface CentralProcessingHandler ()<YTKChainRequestDelegate, UIAlertViewDelegate>
{
    NSInteger chainIndex;
}

// -------------- 普通请求的标识 参数 model名字 数组
@property (nonatomic ,strong) NSMutableArray<NSString *> *identifiersArr;
@property (nonatomic, strong) NSMutableArray<NSDictionary *> *paramsArr;
@property (nonatomic, strong) NSMutableArray<NSString *> *modelArr;

// -------------- 链式请求的 ----------
@property (nonatomic, strong) NSMutableArray<NSArray<NSString *> *> *chainIdentifierArr;
@property (nonatomic, strong) NSMutableArray<NSArray<NSDictionary *> *> *chainParamsArr;
@property (nonatomic, strong) NSMutableArray<NSArray<NSString *> *> *chainModelArr;

// 用来保存需要服务器返回需要缓存的数据
@property (nonatomic, strong) NSMutableArray *cacheDicArray;

// -------- 链式请求的回调,暂时没多大用处,方便扩展 ----
@property (nonatomic, copy) SuccessfulBlock success_;
@property (nonatomic, copy) FailedBlock failed_;

@end

@implementation CentralProcessingHandler

+ (CentralProcessingHandler *)sharedInstance
{
    static id handler = nil;

    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        handler = [[CentralProcessingHandler alloc]init];
        [LCNetworkConfig setupNetworkConfig];
    });
    return handler;
}

#pragma mark- getting
- (NSMutableArray *)identifiersArr
{
    if (_identifiersArr == nil)
    {
        _identifiersArr = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _identifiersArr;
}

- (NSMutableArray *)paramsArr
{
    if (_paramsArr == nil)
    {
        _paramsArr = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _paramsArr;
}

- (NSMutableArray *)modelArr
{
    if (_modelArr == nil)
    {
        _modelArr = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _modelArr;
}

- (NSMutableArray *)cacheDicArray
{
    if (_cacheDicArray == nil)
    {
        _cacheDicArray = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _cacheDicArray;
}

- (NSMutableArray *)chainIdentifierArr
{
    if (_chainIdentifierArr == nil)
    {
        _chainIdentifierArr = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _chainIdentifierArr;
}

- (NSMutableArray *)chainParamsArr
{
    if (_chainParamsArr == nil)
    {
        _chainParamsArr = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _chainParamsArr;
}

- (NSMutableArray *)chainModelArr
{
    if (_chainModelArr == nil)
    {
        _chainModelArr = [[NSMutableArray alloc]initWithCapacity:0];
    }
    return _chainModelArr;
}

#pragma mark- 保存传入的参数
- (void)addRequestIdentify:(NSString *)identify ParamsDic:(NSDictionary *)param ModelName:(NSString *)name
{
    @synchronized(self)
    {
        [self.identifiersArr addObject:identify];
        [self.paramsArr addObject:param?param:@{}];
        [self.modelArr addObject:name];
    }
}

- (void)addRequestIdentifiers:(NSArray<NSString *> *)identifiers ParamsDics:(NSArray<NSDictionary *> *)params ModelName:(NSArray<NSString *> *)name
{
    @synchronized(self)
    {
        [self.identifiersArr addObjectsFromArray:identifiers];
        if (params == nil) {
            WEAKSELF
            [self.identifiersArr enumerateObjectsUsingBlock:^(NSString * _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
                [weakSelf.paramsArr addObject:@{}];
            }];
        }
        else
        {
            [self.paramsArr addObjectsFromArray:params];
        }
        
        [self.modelArr addObjectsFromArray:name];
    }
}

- (void)addChainRequestIdentify:(NSString *)identify ChainParamsDic:(NSDictionary *)param ChainModelName:(NSString *)name
{
    @synchronized(self)
    {
        [self.chainIdentifierArr addObject:@[identify]];
        [self.chainParamsArr addObject:@[param?param:@{}]];
        [self.chainModelArr addObject:@[name]];
    }
}

#pragma mark 保存传入的参数,并且自动发送请求
+ (void)sendRequestWithIdentify:(NSString *)identify Param:(NSDictionary *)param modelName:(NSString *)name SuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock
{
    [self sendRequestWithIdentifiersArr:@[identify] ParamsArr:@[param?param:@{}] modelNameArr:@[name] SuccessBlock:sBlock FaliedBlock:fBlock];
}

+ (void)sendRequestWithIdentifiersArr:(NSArray<NSString *>*)identifiers ParamsArr:(NSArray<NSDictionary *>*)params modelNameArr:(NSArray<NSString *>*)nameArr  SuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock
{
    [[self sharedInstance] addRequestIdentifiers:identifiers ParamsDics:params ModelName:nameArr];
    [[self sharedInstance] sendRequestWithSuccessBlock:sBlock FaliedBlock:fBlock];
}

#pragma mark- 打包参数
/**
 *  打包链式请求的参数
 *
 */
- (NSDictionary *)pakageChainDic
{
    LCNetworkConfig *config = [LCNetworkConfig sharedInstance];
    NSDictionary *dic_ = [config setupRequestConfigWithIdentifiersArray:self.chainIdentifierArr[chainIndex] ParamDicArray:self.chainParamsArr[chainIndex] PostType:PostTypeRequest];
    return dic_;
}

/**
 *  打包普通请求的
 *
 */
- (NSDictionary *)pakageArgument
{
    LCNetworkConfig *config = [LCNetworkConfig sharedInstance];
    // 因为可能多次调用 addRequestIdentify: ParamsDic: ModelName:再发送请求,因此必须整合成一个再发送.
    NSDictionary *dataDic = [config setupRequestConfigWithIdentifiersArray:self.identifiersArr ParamDicArray:self.paramsArr PostType:PostTypeRequest];
    return dataDic;
}

#pragma mark- 上传文件

// 传图片
//+ (void)uploadFileWithFile:(UIImage *)image fileType:(UploadFileType)type ownerId:(NSInteger)o_id fileIndex:(NSInteger)index successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock
//{
//    UploadFileRequest *request = [[UploadFileRequest alloc]initWithImage:image];
//    [request setupUploadImageType:type ownerId:o_id fileIndex:index];
//    [request startWithSuccess:^(YTKBaseRequest *request) {
////        if (type == UploadFileTypeAvatar) // 上传头像需要传回Model,其他不必
////        {
////            !sBlock?:sBlock(@[request],0);
////            return;
////        }
//        NSDictionary *responseDic = request.responseJSONObject;
//        BaseVerificationModel *model = [BaseVerificationModel objectWithKeyValues:responseDic];
//        !sBlock?:sBlock(model?@[model]:@[],0);
//    } failure:^(YTKBaseRequest *request) {
//        !fBlock?:fBlock(request.responseString,0);
//    }];
//}
//
//// 传头像
//+ (void)uploadAvatarWithFile:(UIImage *)image modelName:(NSString *)name successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock
//{
////    [self uploadFileWithFile:image fileType:UploadFileTypeAvatar ownerId:0 fileIndex:1 successfulBlock:^(NSArray *responseObject, double timeStamp) {
////        YTKBaseRequest *request = responseObject.firstObject;
////        NSDictionary *responseDic = request.responseJSONObject;
////        NSInteger code = [[responseDic objectForKey:ResponseCodeKey]integerValue];
////        BOOL success = code == CORRECT_CODE;
////        if (success)
////        {
////            id model = [NSClassFromString(name) objectWithKeyValues:responseDic];
////            NSArray *modelArr = nil;
////            if (model)
////            {
////                modelArr = @[model];
////            }
////            !sBlock?:sBlock(modelArr,0);
////        }
////        else
////        {
////            !fBlock?:fBlock(request.responseString,0);
////        }
////    } failedBlock:^(NSString *errDescription, NSInteger errCode) {
////        
////    }];
//}
//
//// 传视频
//+ (void)uploadVideoWithFileURLString:(NSString *)videoURL ownerId:(NSInteger)o_id successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock
//{
//    UploadFileRequest *request = [[UploadFileRequest alloc]initWithVideoURL:[NSURL URLWithString:videoURL]];
//    [request setupUploadVideoWithOwnerId:o_id];
//    [request startWithSuccess:^(YTKBaseRequest *request) {
//        BaseVerificationModel *model = [BaseVerificationModel objectWithKeyValues:request.responseJSONObject];
//        !sBlock?:sBlock(@[model],0);
//    } failure:^(YTKBaseRequest *request) {
//        BaseVerificationModel *model = [BaseVerificationModel objectWithKeyValues:request.responseJSONObject];
//        !fBlock?:fBlock(model.err,model.ret_code);
//    }];
//}

#pragma mark- 发送请求
- (void)sendRequestWithSuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock
{
    [self sendRequestWithCache:NO SuccessBlock:sBlock FaliedBlock:fBlock];
}

- (void)sendCacheRequestWithSuccessCacheBlock:(SuccessfulCacheBlock)sBlock FaliedBlock:(FailedBlock)fBlock
{
    [self sendRequestWithCache:YES SuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:fBlock];
}

// 发送普通请求
- (void)sendRequestWithCache:(BOOL)cache SuccessBlock:(SuccessfulBlock)sBlock FaliedBlock:(FailedBlock)fBlock
{
    // 每次新的请求之前,都会检测下是否能发送帖子
//    [PostHandler sendCache];
    
    NSDictionary *dic = [self pakageArgument];
    BaseRequest *request_ = [[BaseRequest alloc]initWithArgument:dic];
    request_.cache = cache;
    request_.identifiesArr = self.identifiersArr;
    request_.modelNameArr = self.modelArr;
    
    [self.identifiersArr removeAllObjects];
    [self.paramsArr removeAllObjects];
    [self.modelArr removeAllObjects];
//    //    [[LCNetworkConfig sharedInstance]clearCache];
    
    [request_ startWithSuccess:^(YTKBaseRequest *request) {
        
        if ([[request.responseJSONObject objectForKey:ResponseCodeKey]integerValue] == TimeStampErrCode)
        {
            [self resetTimeInterval:request];
            return;
        }
        [self handleResponseObjectWithBaseRequest:request successfulBlock:sBlock failedBlock:fBlock];
    } failure:^(YTKBaseRequest *request) {
        !fBlock?:fBlock(@"当前网络不可用",request.responseStatusCode);
    }];
}

/**
 *  发送链式请求
 */
- (void)sendChainRequestWithSuccessfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock
{
//    [PostHandler sendCache];
    // 链式目前只支持2个 待扩展
    BaseRequest *foreRequest = [[BaseRequest alloc]initWithArgument:[self pakageChainDic]];
    foreRequest.identifiesArr = self.chainIdentifierArr.firstObject;
    foreRequest.modelNameArr = self.chainModelArr.firstObject;
    
    chainIndex ++;
    //    //    [[LCNetworkConfig sharedInstance]clearCache];
    
    BaseRequest *lastRequest = [[BaseRequest alloc]initWithArgument:[self pakageChainDic]];
    lastRequest.identifiesArr = self.chainIdentifierArr.lastObject;
    lastRequest.modelNameArr = self.chainModelArr.lastObject;
    
    [self resetChainDataArray];
    
    YTKChainRequest *chainReq = [[YTKChainRequest alloc] init];
    [chainReq addRequest:foreRequest callback:^(YTKChainRequest *chainRequest, YTKBaseRequest *baseRequest) {
        BaseRequest *result = (BaseRequest *)baseRequest;
        if (result.responseStatusCode == 200) {
            
            //    //    [[LCNetworkConfig sharedInstance]clearCache];
            [chainRequest addRequest:lastRequest callback:nil];
        }
    }];
    chainReq.delegate = self;
    [chainReq start];
    
    self.success_ = sBlock;
    self.failed_ = fBlock;
}

#pragma mark- 回调,解析模块
#pragma mark 普通请求的回调

- (void)handleResponseObjectWithBaseRequest:(YTKBaseRequest *)request successfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock
{
    NSDictionary *responseDic = request.responseJSONObject;
    BOOL success = [self requestSuccessByResponseObject:responseDic];
    if ([request.responseJSONObject isKindOfClass:[NSDictionary class]] && success)
    {
        NSArray *oriDataArray = [responseDic objectForKey:ResponseDataKey];
        NSMutableArray *cacheDicArray = nil;
        NSArray *oriArr = oriDataArray.firstObject;
        NSDictionary *oriDataDic = oriArr.lastObject;  // 去除时间戳等
        NSArray *modelArray = [self getTagetDataArrayWithObject:oriDataDic BaseRequest:(BaseRequest *)request];
        cacheDicArray = [self.cacheDicArray copy];
        [self resetDataArray];
        
        BaseRequest *base = (BaseRequest *)request;
        if (base.cache)
        {
            SuccessfulCacheBlock cacheBlock = (SuccessfulCacheBlock)sBlock;
            !cacheBlock? : cacheBlock(modelArray ,0,cacheDicArray);
            return;
        }
        !sBlock ? : sBlock(modelArray ,0);
    }
    else
    {
        NSLog(@"--------失败 - %@", request.responseString);
        
        BaseVerificationModel *model = [NSClassFromString(@"BaseVerificationModel") objectWithKeyValues:request.responseString];
        
        if (model.ret_code == 303)   // 如果sessionId 非法
        {
            if ([LCAccount sharedInstance].logined) {
                UIAlertView *alertView = [[UIAlertView alloc] initWithTitle:@"您的账号已在其他地方登录，请重新登录" message:nil delegate:self cancelButtonTitle:@"确定" otherButtonTitles:nil];
                [alertView show];
                [LCAccount sharedInstance].logined = NO;
            }
        }
        NSString *key = [NSString stringWithFormat:@"%ld",request.responseStatusCode];
        !fBlock?:fBlock(NSLocalizedString(key, nil),request.responseStatusCode);
    }
}

#pragma mark 链式请求的回调

- (void)chainRequestFinished:(YTKChainRequest *)chainRequest {
    // all requests are done
    
    NSMutableArray *modelArr = [[NSMutableArray alloc]initWithCapacity:0];
    [chainRequest.requestArray enumerateObjectsUsingBlock:^(id  _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        BaseRequest *request = obj;
        if (self.success_)
        {
            if ([self requestSuccessByResponseObject:request.responseJSONObject]) {
                // 直接获取目标内容
              
                [modelArr addObject:[self getChainTargetDicWithRequest:request]];
            }
        }
    }];
    
    if (self.success_) {
        self.success_(modelArr,0);
        [self setBlockNil];
    }
}

- (void)chainRequestFailed:(YTKChainRequest *)chainRequest failedBaseRequest:(YTKBaseRequest*)request {
    
    if (self.failed_)
    {
        //        [CentralProcessingHandler getRequestResultWithObject:request.responseJSONObject apiIdentify:self.chainIdentifierArr.firstObject.firstObject usingBlock:^(BOOL error, NSString *errDescription, NSInteger errCode, NSDictionary *dicWhenSuccess) {
        //            self.failed_(errDescription,errCode);
        //        }];
        BaseVerificationModel *model = [self getChainTargetDicWithRequest:chainRequest.requestArray.firstObject];
        self.failed_(model.err,model.ret_code);
        [self setBlockNil];
    }
}

#pragma mark 解析普通请求数据,并且返回带model的数组
- (NSArray *)getTagetDataArrayWithObject:(NSDictionary *)dataDicArr BaseRequest:(BaseRequest *)request
{
    WEAKSELF
    NSMutableArray *modelArray = [[NSMutableArray alloc]initWithCapacity:0];
    for (NSString *identify in request.identifiesArr)
    {
        // 如果数组里的identify一样,目前还没遇到这种情况,先预留问题.
        __block NSInteger idIndex = -1;
        [dataDicArr.allKeys enumerateObjectsUsingBlock:^(NSString * _Nonnull key_str, NSUInteger idx, BOOL * _Nonnull stop) {
            if ([identify isEqualToString:key_str]) {
                idIndex = idx;
                *stop = YES;
            }
        }];
        if (idIndex < 0) {
            continue;
        }
        
        NSString *targetModelName = [request.modelNameArr[[request.identifiesArr indexOfObject:identify]] stringByReplacingOccurrencesOfString:@"/" withString:@""];
        NSArray *targetDataArray = dataDicArr.allValues[idIndex];
        NSDictionary *targetDataDic = [targetDataArray.firstObject lastObject];
        NSAssert([targetDataDic isKindOfClass:[NSDictionary class]], @"服务器返回格式有问题");
        NSLog(@"++++当前的api:%@,model名:%@,返回的数据:%@",identify,targetModelName,targetDataDic);
        BaseVerificationModel *model = [NSClassFromString(targetModelName) objectWithKeyValues:targetDataDic];
        
        if (model)
        {
            if (model.ret_code != 200)
            {
                model.err = NSLocalizedString(@(model.ret_code).stringValue, nil);
            }
            [modelArray addObject:model];
            if (request.cache)
            {
                [weakSelf.cacheDicArray addObject:targetDataDic];
            }
            
        }
    }
    return modelArray;
}

#pragma mark 解析链式请求的数据
/**
 *  解析链式请求返回的数据,并且返回目标model
 *
 *  @param request 发出的请求
 *
 *  @return 如果解析没出错,则是目标model;否则是个BaseVerificationModel
 */
- (BaseVerificationModel *)getChainTargetDicWithRequest:(BaseRequest *)request
{
    NSDictionary *oriDataDic = [self getResponseDicByResponseObject:request.responseJSONObject];
    // 链式基本只有1个数据,不过这样写着,预防以后改动.
    if ([oriDataDic isKindOfClass:[NSDictionary class]]) {
        // 链式请求只有1个identify的
        NSString *identify = request.identifiesArr.firstObject;
        NSString *modelName = request.modelNameArr.firstObject;
        NSArray *outArr = oriDataDic[identify];
        NSDictionary *targetDic = [outArr.firstObject lastObject];
        
        NSAssert([targetDic isKindOfClass:[NSDictionary class]], @"链式请求：服务器返回格式出错");
        NSLog(@"-----链式请求的api:%@,model名:%@,返回的数据:%@",identify,modelName,targetDic);
        BaseVerificationModel *model = [NSClassFromString(modelName) objectWithKeyValues:targetDic];
        if (model)
        {
            if (model.ret_code != 200)
            {
                model.err = NSLocalizedString(@(model.ret_code).stringValue, nil);
            }
            return model;
            //链式暂时没遇到需要缓存返回的数据,待扩展[weakSelf.cacheDicArray addObject:targetDic]
        }
    }
    
    return [BaseVerificationModel new];
}


#pragma mark- 一些辅助方法
#pragma mark 解析数据完成后,重设或者清零一些参数
- (void)resetTimeInterval:(YTKBaseRequest *)request
{
    NSDictionary *dic = [request.responseJSONObject objectForKey:ResponseDataKey];
    [[TimeClock sharedInstance]setTimeInterval:[[dic objectForKey:ResponseTimeStampKey]doubleValue]];
//    [[LCNetworkConfig sharedInstance] clearLastCacheItem];
}

- (void)resetDataArray
{
    [self.cacheDicArray removeAllObjects];
    //    [[LCNetworkConfig sharedInstance]clearCache];
}

- (void)resetChainDataArray
{
    [self.chainIdentifierArr removeAllObjects];
    [self.chainParamsArr removeAllObjects];
    [self.chainModelArr removeAllObjects];
    //    [[LCNetworkConfig sharedInstance]clearCache];
    chainIndex = 0;
}

- (void)setBlockNil
{
    self.success_ = nil;
    self.failed_ = nil;
}

/**
 *  检测返回的数据是否成功，通过最外层的返回码（如果通讯出错，在这里会被检查到）。
 *
 *  @param object BaseRequest类的responseJSONObject
 *
 */
- (BOOL)requestSuccessByResponseObject:(id)object
{
    NSInteger code = [[object objectForKey:ResponseCodeKey]integerValue];
    BOOL result =  code == 200;
    return result;
}

/**
 *  返回初始返回的数据中,key为data的字典
 *
 *  @param object BaseRequest类的responseJSONObject
 *
 *  @return <#return value description#>
 */
- (NSDictionary *)getResponseDicByResponseObject:(id)object
{
    NSArray *oriDataArray = object[ResponseDataKey];
    NSArray *oriArr = oriDataArray.firstObject;
    NSDictionary *oriDataDic = oriArr.lastObject;  // 去除时间戳等
    return oriDataDic;
}
#pragma mark 重设时间戳后 重新发送
- (void)resendRequestWithSuccessfulBlock:(SuccessfulBlock)sBlock failedBlock:(FailedBlock)fBlock
{
    
}

#pragma mark- 发送模块
#pragma mark 发送缓存的帖子

//+ (void)sendPostsCache
//{
//    [PostHandler sendPostsCache];
//}
//
//+ (void)resendCacheArray
//{
//    [PostHandler resendCachePosts];
//}

#pragma mark 网络状态监听,并且准备发送缓存的帖子
//+ (void)launchingOpration
//{
//    [self startMonitoring];
//}
//
//+ (void)startMonitoring
//{
//    [[AFNetworkReachabilityManager sharedManager]startMonitoring];
//    [[AFNetworkReachabilityManager sharedManager]setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
//        if (status != AFNetworkReachabilityStatusNotReachable)
//        {
//            [PostHandler resendCachePosts];
//        }
//        switch (status)
//        {
//            case AFNetworkReachabilityStatusNotReachable:
//            {
//                NSLog(@"无网络");
//                break;
//            }
//            case AFNetworkReachabilityStatusReachableViaWiFi:
//            {
//                NSLog(@"WiFi网络");
//                break;
//            }
//            case AFNetworkReachabilityStatusReachableViaWWAN:
//            {
//                NSLog(@"无线网络");
//                break;
//            }
//            default:
//                break;
//        }
//    }];
//}


#pragma mark-

- (void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex {
    if (buttonIndex == 0) {
        [self logOutAct];
    }
}

- (void)logOutAct {
    [LCAccount sharedInstance].logined = NO;
    [LCAccount sharedInstance].sessionId = @"";
    [[LCAccount sharedInstance] logOut];
//    AppDelegate *delegate = [AppDelegate appDelegate];
//    [delegate showLogin];
}

@end
