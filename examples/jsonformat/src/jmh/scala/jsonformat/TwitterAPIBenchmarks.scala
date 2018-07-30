// Copyright: 2010 - 2018 Sam Halliday
// License: http://www.gnu.org/licenses/lgpl-3.0.en.html

// Copyright 2018 Andriy Plokhotnyuk

package jsonformat.benchmarks

import java.util.concurrent.TimeUnit

import org.openjdk.jmh
import jsonformat._
import jsonformat.JsDecoder.ops._
import jsonformat.JsEncoder.ops._
import scalaz._
import Scalaz._
import jsonformat.BenchmarkUtils.getResourceAsString

// sbt clean 'jsonformat/jmh:run TwitterAPIBenchmarks.*'
// or
// sbt clean 'jsonformat/jmh:run -jvm /usr/lib/jvm/graalvm-ee-1.0.0-rc3/bin/java -wi 10 TwitterAPIBenchmarks.*'
//
// see org.openjdk.jmh.runner.options.CommandLineOptions

// reference for the format of tweets: https://developer.twitter.com/en/docs/tweets/search/api-reference/get-search-tweets.html

package z {
  @deriving(JsEncoder, JsDecoder)
  case class Urls(
    url: String,
    expanded_url: String,
    display_url: String,
    indices: IList[Int]
  )

  @deriving(JsEncoder, JsDecoder)
  case class Url(urls: IList[Urls])

  @deriving(JsEncoder, JsDecoder)
  case class UserEntities(url: Url, description: Url)

  @deriving(JsEncoder, JsDecoder)
  case class UserMentions(
    screen_name: String,
    name: String,
    id: Long,
    id_str: String,
    indices: IList[Int]
  )

  @deriving(JsEncoder, JsDecoder)
  case class User(
    id: Long,
    id_str: String,
    name: String,
    screen_name: String,
    location: String,
    description: String,
    url: String,
    entities: UserEntities,
    `protected`: Boolean,
    followers_count: Int,
    friends_count: Int,
    listed_count: Int,
    created_at: String,
    favourites_count: Int,
    utc_offset: Int,
    time_zone: String,
    geo_enabled: Boolean,
    verified: Boolean,
    statuses_count: Int,
    lang: String,
    contributors_enabled: Boolean,
    is_translator: Boolean,
    is_translation_enabled: Boolean,
    profile_background_color: String,
    profile_background_image_url: String,
    profile_background_image_url_https: String,
    profile_background_tile: Boolean,
    profile_image_url: String,
    profile_image_url_https: String,
    profile_banner_url: String,
    profile_link_color: String,
    profile_sidebar_border_color: String,
    profile_sidebar_fill_color: String,
    profile_text_color: String,
    profile_use_background_image: Boolean,
    has_extended_profile: Boolean,
    default_profile: Boolean,
    default_profile_image: Boolean,
    following: Boolean,
    follow_request_sent: Boolean,
    notifications: Boolean,
    translator_type: String
  )

  @deriving(JsEncoder, JsDecoder)
  case class Entities(
    hashtags: IList[String],
    symbols: IList[String],
    user_mentions: IList[UserMentions],
    urls: IList[Urls]
  )

  @deriving(JsEncoder, JsDecoder)
  case class RetweetedStatus(
    created_at: String,
    id: Long,
    id_str: String,
    text: String,
    truncated: Boolean,
    entities: Entities,
    source: String,
    in_reply_to_status_id: Option[String],
    in_reply_to_status_id_str: Option[String],
    in_reply_to_user_id: Option[String],
    in_reply_to_user_id_str: Option[String],
    in_reply_to_screen_name: Option[String],
    user: User,
    geo: Option[String],
    coordinates: Option[String],
    place: Option[String],
    contributors: Option[String],
    is_quote_status: Boolean,
    retweet_count: Int,
    favorite_count: Int,
    favorited: Boolean,
    retweeted: Boolean,
    possibly_sensitive: Boolean,
    lang: String
  )

  @deriving(JsEncoder, JsDecoder)
  case class Tweet(
    created_at: String,
    id: Long,
    id_str: String,
    text: String,
    truncated: Boolean,
    entities: Entities,
    source: String,
    in_reply_to_status_id: Option[String],
    in_reply_to_status_id_str: Option[String],
    in_reply_to_user_id: Option[String],
    in_reply_to_user_id_str: Option[String],
    in_reply_to_screen_name: Option[String],
    user: User,
    geo: Option[String],
    coordinates: Option[String],
    place: Option[String],
    contributors: Option[String],
    retweeted_status: RetweetedStatus,
    is_quote_status: Boolean,
    retweet_count: Int,
    favorite_count: Int,
    favorited: Boolean,
    retweeted: Boolean,
    possibly_sensitive: Boolean,
    lang: String
  )
}

package m {
  case class Urls(
    url: String,
    expanded_url: String,
    display_url: String,
    indices: List[Int]
  )
  case class Url(urls: List[Urls])
  case class UserEntities(url: Url, description: Url)
  case class UserMentions(
    screen_name: String,
    name: String,
    id: Long,
    id_str: String,
    indices: List[Int]
  )
  case class User(
    id: Long,
    id_str: String,
    name: String,
    screen_name: String,
    location: String,
    description: String,
    url: String,
    entities: UserEntities,
    `protected`: Boolean,
    followers_count: Int,
    friends_count: Int,
    listed_count: Int,
    created_at: String,
    favourites_count: Int,
    utc_offset: Int,
    time_zone: String,
    geo_enabled: Boolean,
    verified: Boolean,
    statuses_count: Int,
    lang: String,
    contributors_enabled: Boolean,
    is_translator: Boolean,
    is_translation_enabled: Boolean,
    profile_background_color: String,
    profile_background_image_url: String,
    profile_background_image_url_https: String,
    profile_background_tile: Boolean,
    profile_image_url: String,
    profile_image_url_https: String,
    profile_banner_url: String,
    profile_link_color: String,
    profile_sidebar_border_color: String,
    profile_sidebar_fill_color: String,
    profile_text_color: String,
    profile_use_background_image: Boolean,
    has_extended_profile: Boolean,
    default_profile: Boolean,
    default_profile_image: Boolean,
    following: Boolean,
    follow_request_sent: Boolean,
    notifications: Boolean,
    translator_type: String
  )
  case class Entities(
    hashtags: List[String],
    symbols: List[String],
    user_mentions: List[UserMentions],
    urls: List[Urls]
  )
  case class RetweetedStatus(
    created_at: String,
    id: Long,
    id_str: String,
    text: String,
    truncated: Boolean,
    entities: Entities,
    source: String,
    in_reply_to_status_id: Option[String],
    in_reply_to_status_id_str: Option[String],
    in_reply_to_user_id: Option[String],
    in_reply_to_user_id_str: Option[String],
    in_reply_to_screen_name: Option[String],
    user: User,
    geo: Option[String],
    coordinates: Option[String],
    place: Option[String],
    contributors: Option[String],
    is_quote_status: Boolean,
    retweet_count: Int,
    favorite_count: Int,
    favorited: Boolean,
    retweeted: Boolean,
    possibly_sensitive: Boolean,
    lang: String
  )
  case class Tweet(
    created_at: String,
    id: Long,
    id_str: String,
    text: String,
    truncated: Boolean,
    entities: Entities,
    source: String,
    in_reply_to_status_id: Option[String],
    in_reply_to_status_id_str: Option[String],
    in_reply_to_user_id: Option[String],
    in_reply_to_user_id_str: Option[String],
    in_reply_to_screen_name: Option[String],
    user: User,
    geo: Option[String],
    coordinates: Option[String],
    place: Option[String],
    contributors: Option[String],
    retweeted_status: RetweetedStatus,
    is_quote_status: Boolean,
    retweet_count: Int,
    favorite_count: Int,
    favorited: Boolean,
    retweeted: Boolean,
    possibly_sensitive: Boolean,
    lang: String
  )

  object Urls {
    implicit val encoder: JsEncoder[Urls] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[Urls] = MagnoliaDecoder.gen
  }
  object Url {
    implicit val encoder: JsEncoder[Url] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[Url] = MagnoliaDecoder.gen
  }
  object UserEntities {
    implicit val encoder: JsEncoder[UserEntities] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[UserEntities] = MagnoliaDecoder.gen
  }
  object UserMentions {
    implicit val encoder: JsEncoder[UserMentions] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[UserMentions] = MagnoliaDecoder.gen
  }
  object User {
    implicit val encoder: JsEncoder[User] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[User] = MagnoliaDecoder.gen
  }
  object Entities {
    implicit val encoder: JsEncoder[Entities] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[Entities] = MagnoliaDecoder.gen
  }
  object RetweetedStatus {
    implicit val encoder: JsEncoder[RetweetedStatus] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[RetweetedStatus] = MagnoliaDecoder.gen
  }
  object Tweet {
    implicit val encoder: JsEncoder[Tweet] = MagnoliaEncoder.gen
    implicit val decoder: JsDecoder[Tweet] = MagnoliaDecoder.gen
  }

}
@jmh.annotations.State(jmh.annotations.Scope.Thread)
@jmh.annotations.Warmup(iterations = 7, time = 1, timeUnit = TimeUnit.SECONDS)
@jmh.annotations.Measurement(
  iterations = 7,
  time = 1,
  timeUnit = TimeUnit.SECONDS
)
@jmh.annotations.Fork(
  value = 1,
  jvmArgs = Array(
    "-Xss2m",
    "-Xms2g",
    "-Xmx2g",
    "-XX:+UseG1GC",
    "-XX:+AlwaysPreTouch"
  )
)
@jmh.annotations.BenchmarkMode(Array(jmh.annotations.Mode.Throughput))
@jmh.annotations.OutputTimeUnit(TimeUnit.SECONDS)
class TwitterAPIBenchmarks {
  var jsonString: String  = _
  var jsonString2: String = _
  var jsonString3: String = _
  var objz: List[z.Tweet] = _
  var objm: List[m.Tweet] = _
  var ast1: JsValue       = _
  var ast2: JsValue       = _

  @jmh.annotations.Setup
  def setup(): Unit = {
    jsonString = getResourceAsString("twitter_api_response.json")
    jsonString2 = getResourceAsString("twitter_api_compact_response.json")
    jsonString3 = getResourceAsString("twitter_api_error_response.json")
    ast1 = JsParser(jsonString).getOrElse(null)
    ast2 = JsParser(jsonString3).getOrElse(null)
    objz = decodeScalazDeriving().getOrElse(null)
    require(CompactPrinter(encodeScalazDeriving()) == jsonString2)
    objm = decodeMagnolia().getOrElse(null)
    require(CompactPrinter(encodeMagnolia()) == jsonString2)
    require(decodeScalazDerivingError.isLeft)
    require(decodeMagnoliaError.isLeft)
  }

  @jmh.annotations.Benchmark
  def decodeScalazDeriving(): \/[String, List[z.Tweet]] =
    ast1.as[List[z.Tweet]]

  @jmh.annotations.Benchmark
  def decodeScalazDerivingError(): \/[String, List[z.Tweet]] =
    ast2.as[List[z.Tweet]]

  @jmh.annotations.Benchmark
  def encodeScalazDeriving(): JsValue = objz.toJson

  @jmh.annotations.Benchmark
  def decodeMagnolia(): \/[String, List[m.Tweet]] =
    ast1.as[List[m.Tweet]]

  @jmh.annotations.Benchmark
  def decodeMagnoliaError(): \/[String, List[m.Tweet]] =
    ast2.as[List[m.Tweet]]

  @jmh.annotations.Benchmark
  def encodeMagnolia(): JsValue = objm.toJson

}
