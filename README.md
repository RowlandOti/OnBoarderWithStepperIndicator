## Status
[![Build Status](https://travis-ci.org/RowlandOti/OnBoarderWithStepperIndicator.svg?branch=master)](https://travis-ci.org/RowlandOti/OnBoarderWithStepperIndicator)

## OnBoarderWithStepperIndicator
An onboarding stepper screen library that allows developers to easily create a non-intrusive first-use experience to teach users how to use your app without overloading and frustrating them. 

## Features
- View pager with Stepper Indicator for Showing step levels.
- Footer for exposing custom actions.
- Customizable fragment layouts.

## Preview: 
Coming soon!

![Alt text](https://github.com/RowlandOti/OnBoarderWithStepperIndicator/blob/master/art/framed/Hero-Image_Nexus.jpg?raw=true "MovieSquire Preview")

![Alt text](https://github.com/RowlandOti/MovieSquire/blob/master/art/squire.gif?raw=true "MovieSquire Preview")

## Usage
Coming soon!

## Download
Download [the latest JAR][1] or grab via Maven:
```xml
<dependency>
  <groupId>com.rowland.onboarderwithstepperindicator</groupId>
  <artifactId>stepper-onboarder</artifactId>
  <version>beta-1.0.0</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
repositories {
  jcenter()
}

dependencies {
  compile 'com.rowland.onboarderwithstepperindicator:stepper-onboarder:beta-1.0.0'
}
```
## Configuration
Coming soon!

## Design Guide
For a more appealing appearance ensure that:-
- Indicator color and Line Done color are the same. e.g.

```java
 	getStepperIndicatorManager().setStepperIndicatorColor(getResources().getColor(R.color.orange));
 	getStepperIndicatorManager().setStepperLineDoneColor(getResources().getColor(R.color.orange));
```
- Circle color and Line color are the same. e.g.


```java
 	getStepperIndicatorManager().setStepperCircleColor(getResources().getColor(R.color.grey));
 	getStepperIndicatorManager().setStepperLineColor(getResources().getColor(R.color.grey));
```

## Contact
- Twitter: [@rowlandoti][2]

## Credits
Coming soon!

## Licence
```
Copyright 2016 rowland.oti

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[1]: https://search.maven.org/remote_content?g=com.rowland.onboarderwithstepperindicator&a=stepper-onboarder&v=LATEST
[2]: https://www.twitter.com/rowlandoti
