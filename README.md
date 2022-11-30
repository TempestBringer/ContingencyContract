# ContingencyContract
Play "Contingency Contract" in Minecraft. Designed for hardcore players.Spigot server(and forks) only.

## Install
Just drag .jar file into plugin folder.

## Configure
Configuration File is in plugin/ContingencyContract/config.yml, open and edit.
### Change "Contract" Difficulty
You can see many values in config like below:
```text
  CreeperFusion:
    visible: true
    localized_name:
      cn_zh: 聚变
      en: Fusion
    localized_description:
      cn_zh: 所有Creeper的生命值提升<!>%,爆炸充能时间减少<!>%,爆炸范围提升<!>%
      en: Creeper max health +<!>%,charge time -<!>%,explode radius +<!>%
    level0:
      - 40.0
      - 80.0
      - 150.0
    level1:
      - 0.0
      - 40.0
      - 80.0
    level2:
      - 50.0
      - 100.0
      - 200.0
```
There are many "<!>" in "localized_description". This is a template and will be changed later.

The first "<!>" will be replaced to available values in 'level0', second "<!>" replaced to 'level1' etc.

### Multi-Language Support
In config file you can see text like below:
```text
General:
  Language: cn_zh
```
The text in "General.Language" will be used as a key to find localized translation in every contract config's "localized_name" and "localized_description"
this also works in "Information" configuration.

IMPORTANT : Make sure the language you select exist in EVERY "localized_name" & "localized_description" & "Information" !!!



