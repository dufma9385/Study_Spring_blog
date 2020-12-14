```sh
$ vue create imgur-uploader
$ cd imgur-uploader
$ npm i loadash qs axios
$ vue add router
$ vue add vuex



```

#### 1. 로그인 버튼을 누르면, imgur OAUTH 페이지로 이동

#### 2. 사용자가 imgur 에서 권한을 준다.

#### 3. IMGUR 가 AccessToken 과 함께 우리 App으로 redirect

#### 4. /oauth2/callback 으로 오면, `AuthHandler` 컴포넌트 렌더

#### 5. `AuthHandler` 컴포넌트에서 action(`finalizeLogin`) 실행

#### 6. `finalizeLogin` 에서 토큰 추출 + state 갱신

#### 7. `/` 으로 리다이렉트

