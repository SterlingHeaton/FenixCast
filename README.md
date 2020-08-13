# FenixCastCore
Minecraft plugin developed for the FenixCast server to combine some of the datapacks.

## Features
 **Shulker Drops** - Always drops 2 shulker shells upon death
 **Dragon Drops** - Always drops a dragon egg along with an elytra
 **Random Login Colors** - Random color assigned to your name when you join the server
 **Coordinates Hud** - Uses the action bar to display your XYZ, Direction, and Time
 **Durability Ping** - Warns you with a title message and note block tone when your tool is below 5%, stops when durability reaches 4% (by default)
 **Silence Mobs** - Silence Mobs with a nametag named "silence"
 **Villager Workstation Highlighter** - Use a command and right click a villager to highlight they workstation
 **Item Average Calculator** - Use a command to get an armor stand, place that armor stand on the block where you want to calculate how many items (on average) go though that spot in a hour
 **Kill Empty Boats** - Admin command used to reduce the empty boats currently loaded

## Commands
**Durability Ping** - Enabled by default, disable or enable though this command.
> /DurabilityPing

**Coordinates Hud** - Disabled by default, disable or enable though this command.

> /CoordinatesHud

**Villager Workstation Highlighter** - Use this command to become a workstation finder, right click any villager with a profession to find their workstation. Type the same command to disable the feature.
>/VillagerWorkshop or /vws

[Example on how to use](https://i.imgur.com/cipBkzk.mp4)

**Item Average Calculator** - Use this command to get an armor stand, place that armor stand in a water stream and it will calculate how many item it goes though it in the specified amount of time and average that out to the hour. **Armor stand must be placed on the ground, not the side of a block.**
>/ItemAverage item (time) - Time is in minutes, between 1-60
>/ItemAverage stop - Used to stop the test or if you are getting the "already started test" error message.

[Example on how to use](https://i.imgur.com/6WFOaAv.mp4)

**Kill Empty Boats** - Admin command used to remove any empty boats currently being loaded in the worlds.
>/KillBoats - Command node: fenixcast.killboats (OPs dont need it)
