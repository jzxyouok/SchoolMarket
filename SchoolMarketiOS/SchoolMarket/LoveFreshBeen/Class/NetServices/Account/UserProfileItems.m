//
//  UserProfileItems.m
//  fish
//
//  Created by HuZeSen on 15/12/9.
//  Copyright © 2015年 GZLC. All rights reserved.
//

#import "UserProfileItems.h"
#import "MJExtension.h"

@implementation UserProfileItems

- (void)encodeWithCoder:(NSCoder *)encoder
{
    [encoder encodeInteger:self.ret_code forKey:@"ret_code"];
    [encoder encodeObject:self.err forKey:@"err"];
    [encoder encodeObject:self.data forKey:@"data"];
}

- (instancetype)initWithCoder:(NSCoder *)decoder
{
    self.ret_code = [decoder decodeIntegerForKey:@"ret_code"];
    self.err = [decoder decodeObjectForKey:@"err"];
    self.data = [decoder decodeObjectForKey:@"data"];
    return self;
}

@end

@implementation UserProfileItem

+ (NSDictionary *)replacedKeyFromPropertyName
{
    return @{@"u_description":@"description"};
}

+ (NSDictionary *)objectClassInArray{
    return @{@"tags" : [TagItem class]};
}

- (void)encodeWithCoder:(NSCoder *)encoder
{
    [encoder encodeObject:self.u_description forKey:@"u_description"];
    [encoder encodeObject:self.integral forKey:@"integral"];
    [encoder encodeObject:self.catch_count forKey:@"catch_count"];
    [encoder encodeObject:self.follow_count forKey:@"follow_count"];
    [encoder encodeObject:self.fans_count forKey:@"fans_count"];
    [encoder encodeObject:self.nick forKey:@"nick"];
    [encoder encodeObject:self.fish_number forKey:@"fish_number"];
    [encoder encodeObject:self.username forKey:@"username"];
    [encoder encodeObject:self.role forKey:@"role"];
    [encoder encodeObject:self.face_b forKey:@"face_b"];
    [encoder encodeObject:self.face_m forKey:@"face_m"];
    [encoder encodeObject:self.u_id forKey:@"u_id"];
    [encoder encodeObject:self.gender forKey:@"gender"];
    [encoder encodeObject:self.face_s forKey:@"face_s"];
    [encoder encodeObject:self.province_id forKey:@"province_id"];
    [encoder encodeObject:self.city_id forKey:@"city_id"];
    [encoder encodeObject:self.allow_push_chat forKey:@"allow_push_chat"];
    [encoder encodeObject:self.allow_push_notice forKey:@"allow_push_notice"];
    [encoder encodeObject:self.birthday forKey:@"brithday"];
    [encoder encodeObject:self.phone forKey:@"phone"];
    [encoder encodeObject:self.shipping_address forKey:@"shipping_address"];
    [encoder encodeObject:self.city_name forKey:@"city_name"];
    [encoder encodeObject:self.province_name forKey:@"province_name"];
    [encoder encodeObject:self.shop_count forKey:@"shop_count"];
    [encoder encodeObject:self.ground_count forKey:@"ground_count"];
    [encoder encodeObject:self.age forKey:@"age"];
    [encoder encodeObject:self.job forKey:@"job"];
    [encoder encodeObject:self.o_uid forKey:@"o_uid"];
    [encoder encodeObject:self.qq_uid forKey:@"qq_uid"];
    [encoder encodeObject:self.grade forKey:@"grade"];
    [encoder encodeObject:self.invite forKey:@"invite"];
    [encoder encodeObject:self.team_id forKey:@"team_id"];
    [encoder encodeObject:self.have_team forKey:@"have_team"];
    [encoder encodeObject:self.weibo_uid forKey:@"weibo_uid"];
    [encoder encodeObject:self.body_weight forKey:@"body_weight"];
    [encoder encodeObject:self.body_height forKey:@"body_height"];
    [encoder encodeObject:self.field_position forKey:@"field_position"];
    [encoder encodeObject:self.kick forKey:@"kick"];
    [encoder encodeObject:self.boots forKey:@"boots"];
    [encoder encodeObject:self.moments forKey:@"moments"];
    [encoder encodeObject:self.number forKey:@"number"];
    [encoder encodeObject:self.matches forKey:@"matches"];
    [encoder encodeObject:self.goals forKey:@"goals"];
    [encoder encodeObject:self.assists forKey:@"assists"];
    [encoder encodeObject:self.field_position_id forKey:@"field_position_id"];
    [encoder encodeObject:self.win_count forKey:@"win_count"];
    [encoder encodeObject:self.win_rate forKey:@"win_rate"];
    [encoder encodeObject:self.draw_count forKey:@"draw_count"];
    [encoder encodeObject:self.draw_rate forKey:@"draw_rate"];
    [encoder encodeObject:self.lose_count forKey:@"lose_count"];
    [encoder encodeObject:self.lose_rate forKey:@"lose_rate"];
    [encoder encodeObject:self.goal_count forKey:@"goal_count"];
    [encoder encodeObject:self.assists_count forKey:@"assists_count"];
    [encoder encodeObject:self.match_count forKey:@"match_count"];
    [encoder encodeObject:self.avg_goal_count forKey:@"avg_goal_count"];
    [encoder encodeObject:self.avg_assists_count forKey:@"avg_assists_count"];
    [encoder encodeObject:self.tags forKey:@"tags"];
    [encoder encodeObject:self.cover forKey:@"cover"];
    [encoder encodeObject:self.zone_id forKey:@"zone_id"];
    [encoder encodeObject:self.zone_name forKey:@"zone_name"];
    [encoder encodeObject:self.home_town_province_id forKey:@"home_town_province_id"];
    [encoder encodeObject:self.home_town_province_name forKey:@"home_town_province_name"];
    [encoder encodeObject:self.home_town_city_id forKey:@"home_town_city_id"];
    [encoder encodeObject:self.home_town_city_name forKey:@"home_town_city_name"];
    [encoder encodeObject:self.home_town_zone_id forKey:@"home_town_zone_id"];
    [encoder encodeObject:self.home_town_zone_name forKey:@"home_town_zone_name"];

}

- (instancetype)initWithCoder:(NSCoder *)decoder
{
    self.u_description = [decoder decodeObjectForKey:@"u_description"];
    self.integral = [decoder decodeObjectForKey:@"integral"];
    self.catch_count = [decoder decodeObjectForKey:@"catch_count"];
    self.follow_count = [decoder decodeObjectForKey:@"follow_count"];
    self.fans_count = [decoder decodeObjectForKey:@"fans_count"];
    self.nick = [decoder decodeObjectForKey:@"nick"];
    self.fish_number = [decoder decodeObjectForKey:@"fish_number"];
    self.username = [decoder decodeObjectForKey:@"username"];
    self.role = [decoder decodeObjectForKey:@"role"];
    self.face_b = [decoder decodeObjectForKey:@"face_b"];
    self.face_m = [decoder decodeObjectForKey:@"face_m"];
    self.u_id = [decoder decodeObjectForKey:@"u_id"];
    self.gender = [decoder decodeObjectForKey:@"gender"];
    self.face_s = [decoder decodeObjectForKey:@"face_s"];
    self.province_id = [decoder decodeObjectForKey:@"province_id"];
    self.city_id = [decoder decodeObjectForKey:@"city_id"];
    self.allow_push_notice = [decoder decodeObjectForKey:@"allow_push_notice"];
    self.allow_push_chat = [decoder decodeObjectForKey:@"allow_push_chat"];
    self.birthday = [decoder decodeObjectForKey:@"brithday"];
    self.phone = [decoder decodeObjectForKey:@"phone"];
    self.shipping_address = [decoder decodeObjectForKey:@"shipping_address"];
    self.city_name = [decoder decodeObjectForKey:@"city_name"];
    self.province_name = [decoder decodeObjectForKey:@"province_name"];
    self.shop_count = [decoder decodeObjectForKey:@"shop_count"];
    self.ground_count = [decoder decodeObjectForKey:@"ground_count"];
    self.age = [decoder decodeObjectForKey:@"age"];
    self.job = [decoder decodeObjectForKey:@"job"];
    self.o_uid = [decoder decodeObjectForKey:@"o_uid"];
    self.qq_uid = [decoder decodeObjectForKey:@"qq_uid"];
    self.grade = [decoder decodeObjectForKey:@"grade"];
    self.invite = [decoder decodeObjectForKey:@"invite"];
    self.team_id = [decoder decodeObjectForKey:@"team_id"];
    self.have_team = [decoder decodeObjectForKey:@"have_team"];
    self.weibo_uid = [decoder decodeObjectForKey:@"weibo_uid"];
    self.body_weight = [decoder decodeObjectForKey:@"body_weight"];
    self.body_height = [decoder decodeObjectForKey:@"body_height"];
    self.field_position = [decoder decodeObjectForKey:@"field_position"];
    self.kick = [decoder decodeObjectForKey:@"kick"];
    self.boots = [decoder decodeObjectForKey:@"boots"];
    self.moments = [decoder decodeObjectForKey:@"moments"];
    self.number = [decoder decodeObjectForKey:@"number"];
    self.matches = [decoder decodeObjectForKey:@"matches"];
    self.goals = [decoder decodeObjectForKey:@"goals"];
    self.assists = [decoder decodeObjectForKey:@"assists"];
    self.field_position_id = [decoder decodeObjectForKey:@"field_position_id"];
    self.cover = [decoder decodeObjectForKey:@"cover"];
    self.win_count = [decoder decodeObjectForKey:@"win_count"];
    self.win_rate = [decoder decodeObjectForKey:@"win_rate"];
    self.draw_count = [decoder decodeObjectForKey:@"draw_count"];
    self.draw_rate = [decoder decodeObjectForKey:@"draw_rate"];
    self.lose_count = [decoder decodeObjectForKey:@"lose_count"];
    self.lose_rate = [decoder decodeObjectForKey:@"lose_rate"];
    self.goal_count = [decoder decodeObjectForKey:@"goal_count"];
    self.assists_count = [decoder decodeObjectForKey:@"assists_count"];
    self.tags = [decoder decodeObjectForKey:@"tags"];
    self.match_count = [decoder decodeObjectForKey:@"match_count"];
    self.avg_goal_count = [decoder decodeObjectForKey:@"avg_goal_count"];
    self.avg_assists_count = [decoder decodeObjectForKey:@"avg_assists_count"];
    self.zone_id = [decoder decodeObjectForKey:@"zone_id"];
    self.zone_name = [decoder decodeObjectForKey:@"zone_name"];
    self.home_town_zone_name = [decoder decodeObjectForKey:@"home_town_zone_name"];
    self.home_town_zone_id = [decoder decodeObjectForKey:@"home_town_zone_id"];
    self.home_town_city_name = [decoder decodeObjectForKey:@"home_town_city_name"];
    self.home_town_city_id = [decoder decodeObjectForKey:@"home_town_city_id"];
    self.home_town_province_name = [decoder decodeObjectForKey:@"home_town_province_name"];
    self.home_town_province_id = [decoder decodeObjectForKey:@"home_town_province_id"];
    return self;
}
@end

@implementation TagItem

- (void)encodeWithCoder:(NSCoder *)encoder {
    [encoder encodeObject:self.tag_id forKey:@"tag_id"];
    [encoder encodeObject:self.tag forKey:@"tag"];
}

- (instancetype)initWithCoder:(NSCoder *)decoder {
    self.tag_id = [decoder decodeObjectForKey:@"tag_id"];
    self.tag = [decoder decodeObjectForKey:@"tag"];
    return self;
}


@end

