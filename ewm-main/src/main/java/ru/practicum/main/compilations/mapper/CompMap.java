package ru.practicum.main.compilations.mapper;

import ru.practicum.main.compilations.dto.CompilationDto;
import ru.practicum.main.compilations.dto.NewCompilationDto;
import ru.practicum.main.compilations.model.Compilation;
import ru.practicum.main.events.dto.EventFullDto;
import ru.practicum.main.events.model.Event;

import java.util.ArrayList;
import java.util.List;

public class CompMap {
    public static Compilation mapToCompilations(NewCompilationDto newCompilationDto, Event event){
        return Compilation.builder()
                .events(event)
                .pinned(newCompilationDto.isPinned())
                .title(newCompilationDto.getTitle())
                .build();
    }

    public static CompilationDto mapToCompilationDto (Compilation compilation, List<EventFullDto> listEvent){
        return CompilationDto.builder()
                .id(compilation.getId())
                .events(listEvent)
                .pinned(compilation.isPinned())
                .title(compilation.getTitle())
                .build();
    }

    public static List<CompilationDto> mapToListCompilationDto (List<Compilation> listCompilation, List<EventFullDto> listEvent){
        List<CompilationDto> listCompilationDto = new ArrayList<>();
        for(Compilation comp : listCompilation){
            listCompilationDto.add(mapToCompilationDto(comp, listEvent));
        }

        return listCompilationDto;
    }
}
