package com.service.stajnet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "foreignLanguages")
public class ForeignLanguage {
    
    public enum Level{
        A1,
        A2,
        B1,
        B2,
        C1,
        C2,
        NATIVE {
            @Override
            public String toString(){
                return "Native";
            }
        }
    }

    public enum Language{
        ENGLISH {
            @Override
            public String toString(){
                return "English";
            }
        },
        GERMAN {
            @Override
            public String toString(){
                return "German";
            }
        },
        ARABIC {
            @Override
            public String toString(){
                return "Arabic";
            }
        },
        CHINESE {
            @Override
            public String toString(){
                return "Chinese";
            }
        },
        FRENCH {
            @Override
            public String toString(){
                return "French";
            }
        },
        SPANISH {
            @Override
            public String toString(){
                return "Spanish";
            }
        },
        ITALIAN {
            @Override
            public String toString(){
                return "Italian";
            }
        },
        JAPANESE {
            @Override
            public String toString(){
                return "Japanese";
            }
        },
        PORTUGUESE {
            @Override
            public String toString(){
                return "Portuguese";
            }
        },
        RUSSIAN {
            @Override
            public String toString(){
                return "Russian";
            }
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "languageName", nullable=false)
    @Enumerated(EnumType.STRING)
    private Language languageName;

    @Column(name = "readingLevel", nullable=false)
    @Enumerated(EnumType.STRING)
    private Level readingLevel;

    @Column(name = "writingLevel", nullable=false)
    @Enumerated(EnumType.STRING)
    private Level writingLevel;

    @Column(name = "speakingLevel", nullable=false)
    @Enumerated(EnumType.STRING)
    private Level speakingLevel;

    @Column(name = "listeningLevel", nullable=false)
    @Enumerated(EnumType.STRING)
    private Level listeningLevel;

    public String getLanguageName(){
        return this.languageName.toString();
    }

    public String getReadingLevel(){
        return this.readingLevel.toString();
    }

    public String getWritingLevel(){
        return this.writingLevel.toString();
    }

    public String getSpeakingLevel(){
        return this.speakingLevel.toString();
    }

    public String getListeningLevel(){
        return this.listeningLevel.toString();
    }
}