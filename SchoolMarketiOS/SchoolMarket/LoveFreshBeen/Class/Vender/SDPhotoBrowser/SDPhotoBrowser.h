//
//  SDPhotoBrowser.h
//  photobrowser
//
//  Created by aier on 15-2-3.
//  Copyright (c) 2015年 aier. All rights reserved.
//

#import <UIKit/UIKit.h>


@class SDButton, SDPhotoBrowser;

@protocol SDPhotoBrowserDelegate <NSObject>

@required

- (UIImage *)photoBrowser:(SDPhotoBrowser *)browser placeholderImageForIndex:(NSInteger)index;

@optional

- (NSURL *)photoBrowser:(SDPhotoBrowser *)browser highQualityImageURLForIndex:(NSInteger)index;
- (NSURL *)photoBrowser:(SDPhotoBrowser *)browser highQualityAssetsImageURLForIndex:(NSInteger)index;
- (UIImage *)photoBrowser:(SDPhotoBrowser *)browser highQualityImageByContentFileForIndex:(NSInteger)index;


// 返回删除的图片的索引值
- (void)photoBrowser:(SDPhotoBrowser *)browser indexForDeletedImage:(NSInteger)index;
@end


@interface SDPhotoBrowser : UIView <UIScrollViewDelegate>

@property (nonatomic, weak) UIView *sourceImagesContainerView;
@property (nonatomic, assign) NSInteger currentImageIndex;
@property (nonatomic, assign) NSInteger imageCount;

@property (nonatomic) BOOL hideDeleteBtn;   // 隐藏 保存/删除 按钮
// 是否显示删除按钮,会替换掉原来的保存按钮
@property (nonatomic) BOOL showDeleteBtn;

@property (nonatomic, weak) id<SDPhotoBrowserDelegate> delegate;

- (void)show;

@end
