# Диаграмма вариантов использования
---

# Содержание
1 [Диаграмма прецендентов](#use-case_diagram)<br>
2 [Актеры](#actors)<br>
3 [Варианты использования](#use_cases)<br>
3.1 [Остановить/продолжить воспроизведение музыки](#stop_play)<br>
3.2 [Перейти к следующей/предыдущей композиции в списке](#next_previous)<br>
3.3 [Изменить уровень громкости](#change_volume)<br>
3.4 [Включить/отключить функции перемешивания и повтора композиций](#shuffle_repeat)<br>
3.5 [Создать плейлист](#create_playlist)<br>
3.6 [Добавить файлы в плейлист](#add_files)<br>
3.7 [Удалить плейлист](#delete_playlist)


<a name="use-case_diagram"/>

## 1 Диаграмма прецендентов
Диаграмма прецендентов представляет собой следующую диаграмму:

![Диаграмма вариантов использования](Use-case.PNG)

<a name="actors"/>

## 2 Актеры

В данном приложении только один вид актера - собственно пользователь.  

<a name="use_cases"/>

## 3 Варианты использования

<a name="stop_play"/>

### 3.1 Остановить/продолжить воспроизведение музыки
**Описание.** Вариант использования "Остановить/продолжить воспроизведение музыки"
приостанавливает или продолжает воспроизведение композиции.

#### Основной поток.
1. Приложение анализирует состояние воспроизведения;
2. Если композиция воспроизводилась, то выполняется альтернативный поток A2; 
3. Если композиция находилась в состоянии паузы, то выполняется альтернативный поток A3;
4. Вариант исползования завершается.

**Альтернативный поток A1:**
1. Приложение приостанавливает воспроизведение музыки;
2. Возврат к п.4 основного потока.

**Альтернативный поток A2:**
1. Приложение продолжает воспроизведение композиции с позиции остановки;
2. Возврат к п.4 основного потока.

<a name="next_previous"/>

### 3.2 Перейти к следующей/предыдущей композиции в списке
**Описание.** Вариант использования "Перейти к следующей/предыдущей композиции в списке"
переключает текущую композицию на следующую/предыдущую в списке.

#### Основной поток.
1. Пользователь нажимает на кнопку "далее" или "назад";
2. Если была нажата кнопка "далее", то выполняется альтернативный поток А3;
3. Если была нажата кнопка "назад", то выполняется альтернативный поток А4;
4. Вариант использования завершается.

**Альтернативный поток A3:**
1. Приложение проверяет, не является ли данная композиция последней в списке;
2. Если композиция последняя в списке, то приложение проверяет, включена ли функция 
повтора композиций, если не последняя - происходит переход к п.4 A3.
3. Если функция повтора композиций включена, то приложение переходит к выполнению
альтернативного потока A3 текущего варианта использования, если нет -
приложение переходит к варианту использования "Остановить/продолжить воспроизведение музыки";
4. Приложение начинает воспроизводить следующую композицию в списке;
5. Возврат к п.4 основного потока.

**Альтернативный поток A4:**
1. Приложение проверяет, не является ли данная композиция первой в списке;
2. Если композиция первая в списке, то приложение проверяет, включена ли функция
повтора композиций, если не первая - происходит переход к п.4 A4.
3. Если функция повтора композиций включена, то приложение переходит к выполнению
альтернативного потока A2 текущего варианта использования, если нет -
приложение переходит к варианту использования "Остановить/продолжить воспроизведение
музыки";
3. Приложение начинает воспроизводить предыдующую композицию в списке;
4. Возврат к п.4 основного потока.

<a name="change_volume"/>

### 3.3 Изменить уровень громкости
**Описание.** Вариант использования "Изменить уровень громкости" изменяет громкость
воспроизведения композиции.

#### Основной поток.
1. Пользователь передвигает ползунок в определенное положение.
2. Приложение анализирует положение ползунка относительно длины шкалы.
3. Приложение устанавливает уровень громкости в процентном отношении к высоте положении
ползунка в шкале.
4. Вариант использования завершается.

<a name="shuffle_repeat"/>

### 3.4 Включить/отключить функции перемешивания и повтора композиций
**Описание.** Вариант использования "Включить/отключить функции перемешивания и
повтора композиций" позволяет включить или отключить такие функции, как перемешивание
композиций в плейлисте и повтор композиций в плейлисте.

#### Основной поток.
1. Пользователь нажимает на кнопку "перемешивание" или "повтор";
2. Если была нажата кнопка "перемешивание", то выполняется альтернативный поток А5;
3. Если была нажата кнопка "повтор", то выполняется альтернативный поток А6;
4. Вариант использования завершается.

**Альтернативный поток A5:**
1. Приложение проверяет была ли включена функция перемешивание композиций в списке;
2. Если функция была включена, то приложение отключает ее;
3. Если функция была отключена, то приложение включает ее;
4. Возврат к п.4 основного потока.

**Альтернативный поток A6:**
1. Приложение проверяет была ли включена функция повтора композиций в плейлисте;
2. Если фукнция была включена, то приложение отключает ее;
3. Если функция была отключена, то приложение включает ее;
4. Возврат к п.4 основного потока.

<a name="create_playlist"/>

### 3.5 Создать плейлист
**Описание.** Вариант использования "Создать плейлист" создает плейлист.

#### Основной поток.
1. Пользователь нажимает на кнопку "плюс" справа от созданных ранее плейлистов.
2. Появляется окно для ввода, куда пользователь должен ввести название нового плейлиста.
3. Пользователь вводит название.
4. Приложение проверяет название плейлиста, если оно не валидно, то происходит переход к
п.3 текущего варианта использования.
5. Приложение добавляет панель справа от всех ранее созданных плейлистов и дает ему
название, которое ввел пользователь.
6. Вариант использования завершается.

<a name="add_files"/>

### 3.6 Добавить файлы в плейлист
**Описание.** Вариант использования "Добавить файлы в плейлист" добавляет
файлы в плейлист.

#### Основной поток.
1. Пользователь выбирает плейлист, в который хочет добавить музыку.
2. Пользователь выбирает функцию добавить файлы.
3. Появляется окно файлового менеджера.
4. Ползователь выбирает файлы, которые хочет добавить и нажимает кнопку "OK".
5. Приложение проверяет список файлов и отсеивает те, которые не может добавить.
6. Файлы добавляются в плейлист.
7. Вариант использования завершается.

<a name="delete_playlist"/>

### 3.7 Удалить плейлист
**Описание.** Вариант использования "Удалить плейлист" навсегда удаляет
плейлист из библиотеки.

#### Основной поток.
1. Пользователь выбирает плейлист, который хочет удалить.
2. Пользователь выбирает функцию удалить плейлист.
3. Приложение удаляет данный плейлист из общего списка, а также
файл, в котором хранится библиотека данного плейлиста.
4. Вариант использования завершается.