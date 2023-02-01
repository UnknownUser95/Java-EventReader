# EventReader

EventReader is a Java library for reading Linux input events from input devices.

**This is a fun project, not an actual useful library.**

It provides an easy to use interface between the low level character device files and Java by mapping the C struct `input_event` to a Java record.

## Usage

Since there are multiple ways of getting to `/dev/input/eventX`, multiple interfaces are provided. These are the direct number of the event file, the ID (`/dev/input/by-id/*`), and the path (`/dev/input/by-path/*`). The ID method is recommended, as the event number changes quite often and the path is quite long and not identifying.

Getting keyboards or mice ID's can be done with the `getKeyboardsByID` or `getMiceByID` methods respectively. Note that some devices have both mouse and keyboard events, even though they are not a mouse or keyboard.

Once an EventReader is created, a listener must be added, which will receive all inputs (if specified, separator events will be filtered. These are events, which have no meaning other than to separate events into groups).

Once done, the EventReader can be deleted using the `discard` method. Any new calls to the EventReader are ignored. Since closing a buffered reader instantly is not possible, the underlying stream has to get some sort of input. That means a new event has to occur, until then it will remain "open", even if the read event is not complete.

## Resources

- [codes and values](https://elixir.bootlin.com/linux/latest/source/include/uapi/linux/input-event-codes.h)

- [description of codes](https://www.kernel.org/doc/html/v4.15/input/event-codes.html) 
