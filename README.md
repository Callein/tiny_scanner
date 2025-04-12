## 📘 TinyScanner

TinyScanner는 자바로 구현한 간단한 렉서(어휘 분석기)입니다.   
정규 표현식 없이, 입력 파일을 읽고 의미 있는 토큰 단위로 분석합니다.

---
### 📄 프로젝트 설명

이 프로그램은 주어진 코드 파일을 읽어 다음과 같은 토큰(token) 으로 나눕니다.
- 키워드 (e.g.: if, print)
- 식별자 (변수 이름 등)
- 숫자 리터럴 (e.g.: 123)
- 문자열 리터럴 (e.g.: "hello")
- 연산자 (e.g.: =, +, ==)
- 구분자 (e.g.: ;, (, ))
- 주석 (#로 시작하는 줄)
- 불법 식별자 (규칙에 맞지 않는 변수 이름)

--- 
### ⚙️ 작동 방식
- BufferedReader 를 이용해 파일을 한 줄씩 읽음
- 공백, 기호 등을 기준으로 문자 하나씩 분석
- 정해진 규칙에 따라 각 문자열을 토큰으로 구분
- 멀티 문자 연산자(e.g., +=, !=)는 lookahead 처리로 구분
- 식별자는 첫 글자와 구성 문자 기준으로 유효성 검사
- 출력은 토큰과 해당 타입을 한 줄씩 정렬해서 표시

---
### ✅ 토큰 종류

| 토큰 유형             | 예시                                | 설명                                      |
|----------------------|-----------------------------------|-------------------------------------------|
| `keyword`            | `if`, `while`, `print`, `integer` | 예약어로, 특별한 의미를 갖는 단어        |
| `identifier`         | `count`, `name.1`, `_var`         | 사용자가 정의한 변수명 등                 |
| `number literal`     | `10`, `12345`                     | 정수 숫자 값                              |
| `string literal`     | `"Hello"`, `"123"`                | 큰따옴표(`"`)로 감싼 문자열               |
| `assignment operator`| `=`, `+=`, `-=`, `*=`, `/=`       | 값을 할당하는 연산자                      |
| `comparison operator`| `==`, `!=`, `<`, `>`, `<=`, `>=`  | 비교 연산자 (논리 조건)                   |
| `arithmetic operator`| `+`, `-`, `*`, `/`                | 산술 연산자                               |
| `punctuation`        | `;`, `(`, `)`, `{`, `}`           | 구문을 구분하는 구두점                    |
| `comment`            | `#`로 시작하는 줄                       | 주석, 실행에 영향을 주지 않음            |
| `illegal identifier` | `%b`, `7value`, `a&b`             | 식별자 규칙에 맞지 않는 이름              |
---
### 📁 프로젝트 구성
```
TinyScanner/
├── src/TinyScanner.java           # 메인 자바 파일
├── ScannerTestInputs              # 테스트용 입력 파일
├── TinyScanner_NFA_DFA.pdf        # NFA, DFA 다이어그램 
```
---
### 💻 실행 방법 (쉘에서 테스트)
```shell
cd
javac TinyScanner.java
java TinyScanner ../ScannerTestInputs/scan25Input1.txt
```
- 다른 입력 파일로도 실행해볼 수 있습니다.
