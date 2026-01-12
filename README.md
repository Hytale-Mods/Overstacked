# Overstacked

The Overstacked plugin increases the max stack size of several commonly used items to be higher. It also offers a 
powerful config system that allows you to modify the max stack size of any item in the game!

## Default Changes
The plugin will make the following changes by default.

- Ores `25` -> `50`
- Wood Trunks `100` -> `250`
- Wood Planks `100` -> `250`
- Fibre `100` -> `250`
- Soils `100` -> `250`
- Rocks `100` -> `250`
- Petals `100` -> `300`
- Rubble `100` -> `150`
- Crystal Shards `100` -> `150`
- Seeds `100` -> `150`

## Configuration
When you run the game for the first time after installing the mod, a `Darkhax_Overstacked` folder will be generated in 
the mods folder. Inside this folder you will find a `MaxStackSizes.json` file, which is both an example and the
default changes made by the mod. The file can be edited with notepad or any other text editor.

### ItemIds
The `ItemIds` section of the config is a direct map of item IDs to new stack sizes. You can increase or decrease the 
stack size, as long as the size is not less than 1.

```json
{
  "ItemIds": {
    "Rock_Stone": 999,
    "Soil_Dirt": 5
  }
}
```

If you don't want the mod to modify an item, just remove it from the map. All map entries are optional and up to you.

### Regex Patterns
The `Patterns` section allows you to define a regex pattern. Any ID matching the regex will be updated to the new size. 
For example the pattern `^Furniture_.*_Stool$` will match any item that starts with `Furniture_` and ends with `_Stool`.
Regex patterns are very powerful because they allow you to make sweeping changes. They can also be dangerous as there is
a potential for them to match on something you did not account for.

```json
{
  "ItemIds": {
    "Rock_Stone": 999
  },
  "Patterns": {
    "^Furniture_.*_Stool$": 200
  }
}
```