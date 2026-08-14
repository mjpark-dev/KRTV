.PHONY: gen-version

v ?= 1.0.0
code ?= 1

gen-version:
	printf '{\n  "version_code": %s,\n  "version_name": "%s",\n  "apk_name": "KRTV_v%s.apk",\n  "apk_url": "https://github.com/mjpark-dev/KRTV/releases/download/v%s/KRTV_v%s.apk"\n}\n' "$(code)" "$(v)" "$(v)" "$(v)" "$(v)" > version.json
