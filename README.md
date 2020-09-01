# word-crossy-cheater
Find all combination of letters with provided flags to narrow down search results

Command: java -jar Decoder.jar RDWO 4 -prefix W -suffix D -contains R -regex -OR- -exclude D---,O---,R---

Flags:
- prefix: Only includes searches with provided prefix (Ex. -prefix tr,rt,fe)
- suffix: Only includes searches with provided suffix (Ex. -suffix tr,rt,fe)
- contains: Only includes searches that contains the provided string (Ex. -contains tr,rt,fe)
- regex: Only includes searches of provided word crossy regex (Ex. -regex tr---,rt---,f-e--)
- excludes: Exclude the following word crossy regexes (Ex. -exlude tr---,rt---,f-e--)
